package com.example.notification.controller;

import com.example.notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        return emailService.sendEmail(to, subject, body);
    }
}