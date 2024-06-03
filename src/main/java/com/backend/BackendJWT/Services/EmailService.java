package com.backend.BackendJWT.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecoveryCodeEmail(String to, String recoveryCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Recuperación de Contraseña");
        message.setText("Este es tu código de recuperación:\n\n" + recoveryCode);
        mailSender.send(message);
    }
}
