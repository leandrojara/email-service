package com.leandrojara.emailservice.controllers;

import com.leandrojara.emailservice.models.EmailRequest;
import com.leandrojara.emailservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully");
    }
}
