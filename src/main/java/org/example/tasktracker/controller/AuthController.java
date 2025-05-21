package org.example.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.dto.JwtResponseDto;
import org.example.tasktracker.dto.UserLoginRequestDto;
import org.example.tasktracker.dto.UserRegistrationRequestDto;
import org.example.tasktracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<JwtResponseDto> signUp(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {

        authService.createUser(userRegistrationRequestDto);

        JwtResponseDto jwtResponseDto = authService.login(userRegistrationRequestDto);

        return ResponseEntity.ok()
                .body(jwtResponseDto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody UserLoginRequestDto userLoginRequestDto) {

        JwtResponseDto jwtResponseDto = authService.login(userLoginRequestDto);

        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/auth/sing-out")
    public ResponseEntity<String> singOut() {
        return ResponseEntity.noContent().build();
    }
}
