package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.util.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // For login test
import org.springframework.security.authentication.BadCredentialsException; // For login test


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private User user;
    private User userCredentials;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");

        userCredentials = new User();
        userCredentials.setEmail("test@example.com");
        userCredentials.setPassword("plainPassword");
    }

    @Test
    void testRegister_Success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        String response = authService.register(userCredentials);

        assertEquals("User registered successfully!", response);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.register(userCredentials);
        });

        assertEquals("Error: Email is already taken!", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtils.generateJwtToken(any(User.class))).thenReturn("mockedJwtToken");

        String jwt = authService.login(userCredentials);

        assertEquals("mockedJwtToken", jwt);
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            authService.login(userCredentials);
        });

        assertTrue(exception.getMessage().contains("User Not Found with email:"));
    }

    @Test
    void testLogin_InvalidPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authService.login(userCredentials);
        });

        assertEquals("Invalid password!", exception.getMessage());
    }
}
