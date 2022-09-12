package email.controller;

import email.model.MailCodeDto;
import email.model.MailDto;
import email.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("local/mail")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public Boolean sendMail(@RequestBody @Valid MailDto dto){
        return service.sendMail(dto);
    }
    @PostMapping("/send/code")
    public Boolean sendCodeMail(@RequestBody @Valid MailCodeDto dto){
        return service.sendCodeMail(dto);
    }
}
