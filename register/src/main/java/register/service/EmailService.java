package register.service;

public interface EmailService {
    boolean sendMail(String email, String message);
    boolean sendCode(String email, String code);
}
