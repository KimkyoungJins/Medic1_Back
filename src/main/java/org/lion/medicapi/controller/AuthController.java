package org.lion.medicapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lion.medicapi.dto.request.LoginRequest;
import org.lion.medicapi.dto.request.SignInRequest;
import org.lion.medicapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/medic/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid final SignInRequest request) {
        log.info("request[{}]", request);

        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid final LoginRequest request) {
        log.info("request[{}]", request);

        return ResponseEntity.ok((authService.login(request)));
    }
}
