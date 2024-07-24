package org.lion.medicapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.util.HealthTag;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.domain.UserHealthTag;
import org.lion.medicapi.dto.request.TagRequest;
import org.lion.medicapi.exception.APIException;
import org.lion.medicapi.repository.UserHealthTagRepository;
import org.lion.medicapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserHealthTagService {

    private final UserRepository userRepository;
    private final UserHealthTagRepository userHealthTagRepository;

    @Transactional
    public void deleteHealthTag(final User user, final HealthTag healthTag) {
        final Optional<UserHealthTag> optional = userHealthTagRepository.findByUserAndHealthTag(user, healthTag);

        if (optional.isPresent()) {
            final UserHealthTag userHealthTag = optional.get();
            userHealthTagRepository.delete(userHealthTag);
        } else {
            throw new APIException("해당 유저에게는 해당 태그가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public List<HealthTag> updateHealthTag(final User principal, final TagRequest request) {
        final List<HealthTag> healthTagList = request.getHealthTagList();
        final User user = userRepository.findByEmail(principal.getEmail()).get();

        healthTagList.forEach(healthTag -> {
            log.info("healthTag[{}]", healthTag);

            if (userHealthTagRepository.existsByUserAndHealthTag(user, healthTag)) {
                throw new APIException("이미 저장되어 있는 해시태그입니다.", HttpStatus.BAD_REQUEST);
            }

            final List<UserHealthTag> userHealthTagList = user.getUserHealthTagList();
            log.info("userHealthTagList[{}]", userHealthTagList);

            if (userHealthTagList.size() < 3) {
                final UserHealthTag userHealthTag = new UserHealthTag(healthTag, user);
                userHealthTagList.add(userHealthTag);
            } else {
                throw new APIException("해시태그는 유저당 최대 3개까지만 저장 가능합니다.", HttpStatus.BAD_REQUEST);
            }
        });

        return userRepository.save(user)
                .getUserHealthTagList()
                .stream()
                .distinct()
                .map(UserHealthTag::getHealthTag)
                .toList();
    }
}
