package com.example.auth.controller;

import com.example.auth.model.User; // Keep for AuthService interaction if needed, or map from SignupRequest
import com.example.auth.payload.request.LoginRequest;
import com.example.auth.payload.request.SignupRequest;
import com.example.auth.payload.response.JwtResponse;
import com.example.auth.payload.response.MessageResponse;
import com.example.auth.repository.UserRepository; // For fetching user details for JwtResponse
import com.example.auth.service.AuthService;
// Spring Security imports might be needed if using AuthenticationManager directly
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600) // For broader frontend access during dev
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService; // Your existing AuthService

    @Autowired
    UserRepository userRepository; // To fetch User details for JwtResponse

    // Optional: Inject AuthenticationManager if you want to use it directly
    // @Autowired
    // AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // AuthService.login now returns the JWT directly
        String jwt = authService.login(new User(loginRequest.getEmail(), loginRequest.getPassword()));

        // Fetch user details to include in the response (optional, but good practice)
        // Note: User model from DB should ideally not be directly exposed. Map to a DTO if sensitive info exists.
        User userDetails = userRepository.findByEmail(loginRequest.getEmail())
                                     .orElseThrow(() -> new RuntimeException("Error: User not found after login.")); // Should not happen

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        // Map SignupRequest to User model, or modify AuthService to accept SignupRequest
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        // Set other fields if any from signUpRequest

        String message = authService.register(user); // AuthService.register now returns a message

        // Check message or rely on exceptions for error handling from service
        if (message.startsWith("Error:")) { // Simple check, better to use custom exceptions
            return ResponseEntity.badRequest().body(new MessageResponse(message));
        }
        return ResponseEntity.ok(new MessageResponse(message)); // "User registered successfully!"
    }
}