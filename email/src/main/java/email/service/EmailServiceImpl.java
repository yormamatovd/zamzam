package email.service;

import email.model.MailCodeDto;
import email.model.MailDto;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.TreeMap;

@Service

public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${code-template}")
    private String codeTemplate;
    @Value("${spring.mail.subject}")
    private String otpEmailSubject;
    @Value("${spring.mail.host}")
    private String mailFrom;

    @Override
    public Boolean sendMail(MailDto mailDto) {
        return null;
    }

    @Override
    public Boolean sendCodeMail(MailCodeDto dto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(dto.getCode());
            mimeMessageHelper.setFrom(mailFrom);
            mimeMessageHelper.setTo(dto.getToEmail());
            TreeMap<String, String> args = new TreeMap<>();
            args.put("code", dto.getCode());
            String s = geContentFromTemplate(codeTemplate, args);
            mimeMessageHelper.setText(s, true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String geContentFromTemplate(String templatePath, TreeMap<String, String> values) {
        StringWriter stringWriter = new StringWriter();

        try {
            VelocityContext velocityContext = new VelocityContext();
            values.forEach(velocityContext::put);
            velocityEngine.mergeTemplate(templatePath, "UTF-8", velocityContext, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
