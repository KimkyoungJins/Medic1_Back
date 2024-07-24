package org.lion.medicapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.domain.HealthTag;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.dto.request.UpdateHealthTagRequest;
import org.lion.medicapi.service.UserHealthTagService;
import org.lion.medicapi.util.AuthenticationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/medic/api/health-tag")
public class UserHealthTagController {

    private final UserHealthTagService userHealthTagService;

    @DeleteMapping
    public ResponseEntity<?> deleteHealthTag(@RequestParam final HealthTag healthTag) {
        log.info("healthTag[{}]", healthTag);

        final User user = AuthenticationUtils.getUser();
        userHealthTagService.deleteHealthTag(user, healthTag);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> updateHealthTag(@RequestBody final UpdateHealthTagRequest request) {
        log.info("request[{}]", request);

        final User user = AuthenticationUtils.getUser();
        return ResponseEntity.ok(userHealthTagService.updateHealthTag(user, request));
    }
}
