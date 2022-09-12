package email.model;


import email.annotations.Gmail;

import javax.validation.constraints.NotBlank;

public class MailCodeDto {

    @Gmail
    private String toEmail;
    @NotBlank(message = "code is mandatory")
    private String code;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getCode() {
        return code;
    }

    public MailCodeDto(String toEmail, String code) {
        this.toEmail = toEmail;
        this.code = code;
    }

    public MailCodeDto() {
    }

    public void setCode(String code) {
        this.code = code;
    }
}
