package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import com.example.auth.util.JwtUtils; // Add this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Or use RuntimeException
import org.springframework.security.authentication.BadCredentialsException; // Or use RuntimeException

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils; // Add this

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) { // Add JwtUtils
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils; // Add this
    }

    public String register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            // Consider creating a custom exception for better error handling client-side
            throw new RuntimeException("Error: Email is already taken!");
        }

        // Create new user's account
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // Set any other fields from the input 'user' object if necessary

        userRepository.save(newUser);

        return "User registered successfully!";
    }

    public String login(User userCredentials) { // Parameter is the User object from the request
        User user = userRepository.findByEmail(userCredentials.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + userCredentials.getEmail()));

        if (!passwordEncoder.matches(userCredentials.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password!");
        }

        // If using Spring Security UserDetails, you'd typically create/load it here
        // For now, passing the User entity itself to generate token based on its details
        return jwtUtils.generateJwtToken(user);
    }
}