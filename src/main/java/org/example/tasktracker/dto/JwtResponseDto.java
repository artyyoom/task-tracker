package org.example.tasktracker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtResponseDto {
    private String token;
    private Long id;
    private String username;
    private String email;
}
