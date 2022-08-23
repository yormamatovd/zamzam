package register.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import register.service.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final VelocityEngine velocityEngine;

    @Value("${spring.mail.subject}")
    private String otpEmailSubject;
    @Value("${spring.mail.host}")
    private String mailFrom;

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
    public boolean sendCode(String mailTo, String code) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(code);
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setTo(mailTo);
            String s = geContentFromTemplate(code);
            mimeMessageHelper.setText(s, true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String geContentFromTemplate(String code) {
        StringWriter stringWriter = new StringWriter();

        try {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("code", code);
            velocityEngine.mergeTemplate("templates/send-code.vm", "UTF-8", velocityContext, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

