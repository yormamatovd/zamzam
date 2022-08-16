package register.service;

import org.springframework.http.ResponseEntity;
import register.model.ClientDto;
import register.model.register.OtpVerify;
import register.model.register.RegisterUserDto;
import register.model.token.TokenDto;

public interface RegisterEmailService {
    ResponseEntity<Long> verifyRegister(OtpVerify otpVerify);

    ResponseEntity<TokenDto> register(RegisterUserDto regDto);
}
