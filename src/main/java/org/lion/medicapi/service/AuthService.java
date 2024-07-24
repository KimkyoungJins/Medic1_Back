package org.lion.medicapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.domain.UserHealthTag;
import org.lion.medicapi.dto.request.LoginRequest;
import org.lion.medicapi.dto.request.SignInRequest;
import org.lion.medicapi.dto.response.LoginResponse;
import org.lion.medicapi.dto.response.SignUpResponse;
import org.lion.medicapi.exception.APIException;
import org.lion.medicapi.repository.UserHealthTagRepository;
import org.lion.medicapi.repository.UserRepository;
import org.lion.medicapi.security.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserHealthTagRepository userHealthTagRepository;

    @Transactional
    public SignUpResponse signIn(final SignInRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new APIException("중복된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        final User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .birthDt(request.getBirthDt())
                .sexType(request.getSexType())
                .build();

        final List<UserHealthTag> userHealthTagList = new ArrayList<>();
        request.getHealthTagList()
                .forEach(healthTag -> {
                    final UserHealthTag userHealthTag = new UserHealthTag(healthTag, user);
                    userHealthTagList.add(userHealthTag);
                });

        user.setUserHealthTagList(userHealthTagList);
        final User savedUser = userRepository.save(user);

        userHealthTagRepository.saveAll(userHealthTagList);
        return SignUpResponse.of(savedUser);
    }

    public LoginResponse login(final LoginRequest request) throws APIException {

        final User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new APIException("존재하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST));
        log.info("user[{}]", user);

        final String encryptedPassword = user.getPassword();
        final String rawPassword = request.getPassword();

        if (!passwordEncoder.matches(rawPassword, encryptedPassword)) {
            throw new APIException("잘못된 비밀번호입니다.", HttpStatus.UNAUTHORIZED);
        }

        final String accessToken = tokenProvider.createAccessToken(user);
        return LoginResponse.of(user, accessToken);
    }
}
