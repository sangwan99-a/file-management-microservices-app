package com.example.notification.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public String sendEmail(String to, String subject, String body) {
        // Dummy implementation, replace with JavaMailSender
        return "Email sent to " + to + " with subject: " + subject;
    }
}