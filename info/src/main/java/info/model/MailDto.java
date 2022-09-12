package info.model;


public class MailDto {

    private String toEmail;
    private String message;

    public MailDto(String toEmail, String message) {
        this.toEmail = toEmail;
        this.message = message;
    }


    public String getToEmail() {
        return this.toEmail;
    }
    public String getMessage() {
        return this.message;
    }
}
