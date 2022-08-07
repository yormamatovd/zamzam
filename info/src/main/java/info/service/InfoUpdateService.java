package info.service;

import info.model.info.InfoDto;
import info.model.info.UpdateEmailDto;
import info.model.info.UpdateNameSurnameDto;
import info.model.info.UpdatePasswordDto;
import info.model.token.TokenDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface InfoUpdateService {
    ResponseEntity<TokenDto> updatePassword(UpdatePasswordDto updatePasswordDto);

    ResponseEntity<TokenDto> updateEmail(UpdateEmailDto updateEmailDto);

    ResponseEntity<InfoDto> updateName(UpdateNameSurnameDto updateNameSurnameDto);

    ResponseEntity<InfoDto> uploadProfilePhoto(UUID photoId, Long userId);
}
