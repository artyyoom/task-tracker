package org.example.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.dto.JwtResponseDto;
import org.example.tasktracker.dto.UserRequestDto;
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
    public ResponseEntity<JwtResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {

        authService.createUser(userRequestDto);

        JwtResponseDto jwtResponseDto = authService.login(userRequestDto);

        return ResponseEntity.ok()
                .body(jwtResponseDto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody UserRequestDto userRequestDto) {

        JwtResponseDto jwtResponseDto = authService.login(userRequestDto);

        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/auth/sing-out")
    public ResponseEntity<String> singOut() {
        return ResponseEntity.noContent().build();
    }
}
