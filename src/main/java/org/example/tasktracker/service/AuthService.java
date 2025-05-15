package org.example.tasktracker.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.tasktracker.dto.UserRequestDto;
import org.example.tasktracker.dto.UserResponseDto;
import org.example.tasktracker.exception.DataAlreadyExistsException;
import org.example.tasktracker.exception.DataNotFoundException;
import org.example.tasktracker.exception.DatabaseException;
import org.example.tasktracker.exception.InvalidDataException;
import org.example.tasktracker.mapper.UserMapper;
import org.example.tasktracker.model.User;
import org.example.tasktracker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        try {
            User user = userMapper.toUser(userRequestDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

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
            User username = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new DataNotFoundException("User" + user.getUsername() + "not found"));

            return new UserResponseDto(username.getId(), username.getUsername(), username.getEmail());
        } catch (BadCredentialsException e) {
            throw new InvalidDataException("Invalid data");
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Something went wrong");
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
