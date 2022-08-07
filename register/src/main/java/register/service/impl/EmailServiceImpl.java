package register.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import register.service.EmailService;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final VelocityEngine velocityEngine;

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

    @Override
    public boolean sendCode(String email, String code) {
        try {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("code", code);
            StringWriter stringWriter = new StringWriter();

            velocityEngine.mergeTemplate("templates/send-code.vm", "UTF-8", velocityContext, stringWriter);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject(otpEmailSubject);
            msg.setText(stringWriter.toString());
            javaMailSender.send(msg);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
