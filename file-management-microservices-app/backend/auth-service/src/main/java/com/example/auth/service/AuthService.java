package com.example.auth.service;

import com.example.auth.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String register(User user) {
        // Add user registration logic here (save to DB)
        return "User registered: " + user.getEmail();
    }

    public String login(User user) {
        // Add login validation and JWT generation logic
        return "User logged in: " + user.getEmail();
    }
}