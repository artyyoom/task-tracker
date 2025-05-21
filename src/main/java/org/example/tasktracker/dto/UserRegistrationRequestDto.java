package org.example.tasktracker.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegistrationRequestDto implements UserRequestDtoProvider {

    private String username;
    private String password;
    private String email;
}

