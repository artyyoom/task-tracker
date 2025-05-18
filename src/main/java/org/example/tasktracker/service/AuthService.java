package org.example.tasktracker.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.tasktracker.dto.JwtResponseDto;
import org.example.tasktracker.dto.UserRequestDto;
import org.example.tasktracker.dto.UserResponseDto;
import org.example.tasktracker.exception.DataAlreadyExistsException;
import org.example.tasktracker.exception.DataNotFoundException;
import org.example.tasktracker.exception.DatabaseException;
import org.example.tasktracker.exception.InvalidDataException;
import org.example.tasktracker.mapper.UserMapper;
import org.example.tasktracker.model.User;
import org.example.tasktracker.repository.UserRepository;
import org.example.tasktracker.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
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

    public JwtResponseDto login(UserRequestDto userRequestDto) {
        try {

            User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow(() -> new DataNotFoundException(userRequestDto.getUsername()));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequestDto.getUsername(),
                            userRequestDto.getPassword()
                    )
            );

            String token = jwtUtils.generateJwtToken(authentication);

            return JwtResponseDto.builder()
                    .accessToken(token)
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();

        } catch (BadCredentialsException e) {
            throw new InvalidDataException("Invalid data");
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException("Data not found " + e.getMessage());
        } catch (Exception e) {
            throw new DatabaseException("Something went wrong");
        }
    }
}
