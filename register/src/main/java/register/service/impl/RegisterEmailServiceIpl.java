package register.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import register.enums.ApiStatus;
import register.enums.UserType;
import register.exceptions.BadRequestException;
import register.exceptions.NotAcceptableException;
import register.exceptions.SystemException;
import register.feign.ClientTemplate;
import register.feign.EmailTemplate;
import register.feign.MainTemplate;
import register.feign.SellerTemplate;
import register.helper.Helper;
import register.jwt.JWTProvider;
import register.model.ClientDto;
import register.model.MailCodeDto;
import register.model.SellerDto;
import register.model.register.OtpVerify;
import register.model.register.RegisterUserDto;
import register.model.token.ProfileTokenDto;
import register.model.token.TokenDto;
import register.model.token.TokenInfoDto;
import register.service.RegisterEmailService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegisterEmailServiceIpl implements RegisterEmailService {
    private final ClientTemplate clientTemplate;
    private final SellerTemplate sellerTemplate;
    private final EmailTemplate emailTemplate;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MainTemplate mainTemplate;

    @Value("${email.otp.expiration-time-seconds}")
    private Long otpLifetimeSeconds;

    @Override
    public ResponseEntity<Long> verifyRegister(OtpVerify otpVerify) {
        String subject = jwtProvider.getSubjectFromToken(otpVerify.getToken());
        ProfileTokenDto tokenDto = new Gson().fromJson(subject, ProfileTokenDto.class);
        //check expired
        if (tokenDto.getTokenInfo().getExpireTimeSeconds() <= Helper.currentSeconds())
            throw new NotAcceptableException(ApiStatus.VERIFY_CODE_EXPIRED);
        //check code
        if (!tokenDto.getVerifyCode().equals(otpVerify.getCode()))
            throw new BadRequestException(ApiStatus.WRONG_CODE);

        RegisterUserDto registerUserDto = new Gson().fromJson(tokenDto.getBody(), RegisterUserDto.class);

        mainTemplate.checkEmail(registerUserDto.getEmail());
        mainTemplate.checkPhone(registerUserDto.getPhone());

        if (registerUserDto.getType() == UserType.SELLER_USER) {
            try {
                ResponseEntity<SellerDto> response = sellerTemplate.createSeller(registerUserDto);
                return ResponseEntity.ok(Objects.requireNonNull(response.getBody()).getInfo().getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw new SystemException(ApiStatus.SERVER_ERROR.name());
            }
        } else {
            try {
                ResponseEntity<ClientDto> response = clientTemplate.createClient(registerUserDto);
                return ResponseEntity.ok(Objects.requireNonNull(response.getBody()).getInfo().getId());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw new SystemException(ApiStatus.SERVER_ERROR.name());
            }
        }


    }

    @Override
    public ResponseEntity<TokenDto> register(RegisterUserDto registerUserDto) {

        mainTemplate.checkEmail(registerUserDto.getEmail());
        mainTemplate.checkPhone(registerUserDto.getPhone());


        String otpCode = Helper.generateOTP(6);
        emailTemplate.sendCodeMail(new MailCodeDto(registerUserDto.getEmail(), otpCode));

        long currentSeconds = Helper.currentSeconds();
        TokenInfoDto tokenInfoDto = new TokenInfoDto();
        tokenInfoDto.setLifetimeSeconds(otpLifetimeSeconds);
        tokenInfoDto.setCreateTimeSeconds(currentSeconds);
        tokenInfoDto.setExpireTimeSeconds(currentSeconds + otpLifetimeSeconds);

        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        ProfileTokenDto tokenDto = new ProfileTokenDto();
        tokenDto.setVerifyCode(otpCode);
        tokenDto.setBody(new Gson().toJson(registerUserDto));
        tokenDto.setTokenInfo(tokenInfoDto);
        String json = new Gson().toJson(tokenDto);
        String customToken = jwtProvider.generateCustomToken(json);
        return ResponseEntity.ok(new TokenDto(customToken, tokenInfoDto));
    }
}
