package org.example.tasktracker.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.tasktracker.dto.UserRequestDto;
import org.example.tasktracker.dto.UserResponseDto;
import org.example.tasktracker.exception.DataAlreadyExistsException;
import org.example.tasktracker.exception.DatabaseException;
import org.example.tasktracker.exception.InvalidDataException;
import org.example.tasktracker.mapper.UserMapper;
import org.example.tasktracker.model.User;
import org.example.tasktracker.repository.PersonRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
//    private final PersonValidator personValidator;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        try {
//            personValidator.validate(userRequestDto);

            //возможно не получится взять поле id
            User user = userMapper.toUser(userRequestDto);

            personRepository.save(user);

            return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());
        } catch (InvalidDataException e) {
            throw new InvalidDataException(e.getMessage());
        } catch (Exception e) {
            throw new DataAlreadyExistsException("Data already exists");
        }
    }

    public UserResponseDto login(UserRequestDto user, HttpSession session) {
        try {
            authenticateUser(user.getUsername(), user.getPassword(), session);

            //TODO
            Optional<User> username = personRepository.findByUsername(user.getUsername());

            return new UserResponseDto(username.get().getId(), username.get().getUsername(), username.get().getEmail());
        } catch (Exception e) {
            throw new InvalidDataException("Invalid data");
        }
    }

    public void authenticateUser(String username, String password, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
    }
}
