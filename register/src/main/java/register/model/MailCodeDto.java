package register.model;


public class MailCodeDto {

    private String toEmail;
    private String code;

    public MailCodeDto(String toEmail, String code) {
        this.toEmail = toEmail;
        this.code = code;
    }

    public String getToEmail() {
        return this.toEmail;
    }
    public String getCode() {
        return this.code;
    }
}
