package info.service.impl;

import com.google.gson.Gson;
import info.common.MessageService;
import info.entity.Info;
import info.enums.ApiStatus;
import info.enums.TokenActionType;
import info.exception.NotAcceptableException;
import info.exception.NotFoundException;
import info.feign.AttachmentTemplate;
import info.feign.EmailTemplate;
import info.helper.Helper;
import info.jwt.JWTProvider;
import info.mapper.MapstructMapper;
import info.model.MailDto;
import info.model.info.InfoDto;
import info.model.info.UpdateEmailDto;
import info.model.info.UpdateNameSurnameDto;
import info.model.info.UpdatePasswordDto;
import info.model.token.ProfileTokenDto;
import info.model.token.TokenDto;
import info.model.token.TokenInfoDto;
import info.repository.InfoRepo;
import info.service.InfoUpdateService;
import info.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InfoUpdateServiceImpl implements InfoUpdateService {

    private final InfoRepo infoRepo;
    private final EmailTemplate emailTemplate;
    private final JWTProvider jwtProvider;
    private final MapstructMapper mapper;
    private final AttachmentTemplate attachmentTemplate;

    @Value("${email.otp.expiration-time-seconds}")
    private Long otpLifetimeSeconds;
    @Value("${info.default.photo-id}")
    private String defaultPhotoId;

    @Override
    public ResponseEntity<TokenDto> updatePassword(UpdatePasswordDto updatePasswordDto) {
        Optional<Info> userOptional = infoRepo.findById(updatePasswordDto.getUserId());

        if (userOptional.isEmpty())
            throw new NotFoundException(ApiStatus.USER_NOT_FOUND);

        if (!Session.getInfoId().equals(updatePasswordDto.getUserId()))
            throw new NotAcceptableException(ApiStatus.NOT_ACCESS);


        TokenInfoDto tokenInfoDto = new TokenInfoDto();
        tokenInfoDto.setLifetimeSeconds(otpLifetimeSeconds);
        tokenInfoDto.setCreateTimeSeconds(Helper.currentSeconds());
        tokenInfoDto.setExpireTimeSeconds(tokenInfoDto.getCreateTimeSeconds() + otpLifetimeSeconds);

        String otpCode = Helper.generateOTP(6);
        emailTemplate.sendMail(new MailDto(userOptional.get().getEmail(), MessageService.getMessage("UPDATE_PASSWORD_MSG") + " " + otpCode));

        ProfileTokenDto profileTokenDto = new ProfileTokenDto();
        profileTokenDto.setActionType(TokenActionType.UPDATE_PASSWORD);
        profileTokenDto.setVerifyCode(otpCode);
        profileTokenDto.setBody(new Gson().toJson(updatePasswordDto));
        profileTokenDto.setTokenInfo(tokenInfoDto);

        return ResponseEntity.ok(new TokenDto(jwtProvider.generateCustomToken(new Gson().toJson(profileTokenDto)), tokenInfoDto));
    }

    @Override
    public ResponseEntity<TokenDto> updateEmail(UpdateEmailDto updateEmailDto) {
        return null;
    }

    @Override
    public ResponseEntity<InfoDto> updateName(UpdateNameSurnameDto updateNameSurnameDto) {
        if (!Session.getInfoId().equals(updateNameSurnameDto.getUserId()))
            throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        Optional<Info> userOptional = infoRepo.findById(updateNameSurnameDto.getUserId());
        if (userOptional.isEmpty()) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);

        Info info = userOptional.get();

        info.setName(updateNameSurnameDto.getName().trim());
        info.setSurname(updateNameSurnameDto.getSurname().trim());

        infoRepo.save(info);
        return ResponseEntity.ok(mapper.infoToInfoDto(info));
    }

    @Transactional
    @Override
    public ResponseEntity<InfoDto> uploadProfilePhoto(UUID photoId, Long userId) {
        if (!Session.getInfoId().equals(userId)) throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        Optional<Info> userOptional = infoRepo.findById(userId);
        if (userOptional.isEmpty()) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);

        Info info = userOptional.get();

        try {
            attachmentTemplate.detail(photoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException(ApiStatus.FILE_NOT_FOUND);
        }

        if (!Objects.equals(info.getPhotoId(), UUID.fromString(defaultPhotoId)))
            attachmentTemplate.delete(info.getPhotoId());

        info.setPhotoId(photoId);
        infoRepo.save(info);
        return ResponseEntity.ok(mapper.infoToInfoDto(info));
    }

}
