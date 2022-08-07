package client.service.impl;

import client.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.subject}")
    private String otpEmailSubject;

    @Override
    public boolean sendMail(String email, String message) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject(otpEmailSubject);
            msg.setText(message);
            javaMailSender.send(msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
