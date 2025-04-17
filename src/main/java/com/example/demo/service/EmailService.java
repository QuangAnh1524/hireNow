package com.example.demo.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailSender mailSender;

    public EmailService(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ninoel2004@gmail.com");
        message.setSubject("Tesing from Spring boot");
        message.setText("Hello world from spring boot email");
        this.mailSender.send(message);
    }
}
