package org.example.tasktracker.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.tasktracker.dto.UserRequestDto;
import org.example.tasktracker.dto.UserResponseDto;
import org.example.tasktracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto, HttpSession session) {

        UserResponseDto userResponseDto = authService.createUser(userRequestDto);
        authService.login(userRequestDto, session);

        return ResponseEntity.ok()
                .body(userResponseDto);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody UserRequestDto userRequestDto, HttpSession session) {

        UserResponseDto user = authService.login(userRequestDto, session);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/sing-out")
    public ResponseEntity<String> singOut() {
        return ResponseEntity.noContent().build();
    }
}
