package info.service.impl;

import com.google.gson.Gson;
import info.entity.Info;
import info.enums.ApiStatus;
import info.enums.TokenActionType;
import info.exception.BadRequestException;
import info.exception.NotAcceptableException;
import info.exception.NotFoundException;
import info.helper.Helper;
import info.model.info.UpdatePasswordDto;
import info.model.token.CheckCodeDto;
import info.model.token.ProfileTokenDto;
import info.repository.InfoRepo;
import info.jwt.JWTProvider;
import info.session.Session;
import info.service.EmailService;
import info.service.UpdateVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateVerifyServiceImpl implements UpdateVerifyService {

    private final InfoRepo infoRepo;
    private final EmailService emailService;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> updatePasswordVerify(CheckCodeDto dto) {
        String subject = jwtProvider.getSubjectFromToken(dto.getToken());
        ProfileTokenDto tokenDto = new Gson().fromJson(subject, ProfileTokenDto.class);

        //check expired
        if (tokenDto.getTokenInfo().getExpireTimeSeconds() <= Helper.currentSeconds())
            throw new NotAcceptableException(ApiStatus.TOKEN_EXPIRED);
        //check code
        if (!tokenDto.getVerifyCode().equals(dto.getCode()))
            throw new NotAcceptableException(ApiStatus.WRONG_CODE);

        if (tokenDto.getActionType() != TokenActionType.UPDATE_PASSWORD)
            throw new BadRequestException(ApiStatus.BAD_REQUEST);

        UpdatePasswordDto passwordDto = new Gson().fromJson(tokenDto.getBody(), UpdatePasswordDto.class);
        if (!Session.getInfoId().equals(passwordDto.getUserId()))
            throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        Optional<Info> userOptional = infoRepo.findById(passwordDto.getUserId());
        if (userOptional.isEmpty()) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);

        Info info = userOptional.get();
        emailService.sendMail(info.getEmail(),
                "Sizning \"Zilol Suv\" ilovasidagi hisobingiz paroli o`zgartirildi.");

        info.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        infoRepo.save(info);
        return ResponseEntity.ok(ApiStatus.OK.name());
    }
}
