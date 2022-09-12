package email.model;


import email.annotations.Gmail;

import javax.validation.constraints.NotBlank;

public class MailDto {

    @Gmail
    private String toEmail;
    @NotBlank(message = "message is mandatory")
    private String message;

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getMessage() {
        return message;
    }

    public MailDto(String toEmail, String message) {
        this.toEmail = toEmail;
        this.message = message;
    }

    public MailDto() {
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
