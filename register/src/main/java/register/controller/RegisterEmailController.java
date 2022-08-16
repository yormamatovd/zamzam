package register.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import register.enums.UserType;
import register.model.ClientDto;
import register.model.register.OtpVerify;
import register.model.register.RegisterUserDto;
import register.model.token.TokenDto;
import register.service.RegisterEmailService;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterEmailController {

    private final RegisterEmailService registerEmailService;

    @PostMapping
    public ResponseEntity<TokenDto> registerClient(@Valid @RequestBody RegisterUserDto dto) {
        dto.setType(UserType.CLIENT_USER);
        return registerEmailService.register(dto);
    }
    @PostMapping("/seller")
    public ResponseEntity<TokenDto> registerSeller(@Valid @RequestBody RegisterUserDto dto) {
        dto.setType(UserType.SELLER_USER);
        return registerEmailService.register(dto);
    }

    @PostMapping("/verify")
    public ResponseEntity<Long> verifyRegister(@Valid @RequestBody OtpVerify dto) {
        return registerEmailService.verifyRegister(dto);
    }
}
