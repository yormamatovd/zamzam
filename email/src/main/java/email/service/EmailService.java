package email.service;

import email.model.MailCodeDto;
import email.model.MailDto;

public interface EmailService {
    Boolean sendMail(MailDto mailDto);

    Boolean sendCodeMail(MailCodeDto dto);
}
