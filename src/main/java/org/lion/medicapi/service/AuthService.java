package org.lion.medicapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.dto.request.SignUpRequest;
import org.lion.medicapi.exception.APIException;
import org.lion.medicapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signIn(final SignUpRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new APIException("중복된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        final User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .birthDt(request.getBirthDt())
                .sexType(request.getSexType())
                .healthTagList(request.getHealthTagList())
                .build();

        return userRepository.save(user);
    }
}
