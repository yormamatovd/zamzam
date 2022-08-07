package info.controller;

import info.model.info.InfoDto;
import info.model.info.UpdateEmailDto;
import info.model.info.UpdateNameSurnameDto;
import info.model.info.UpdatePasswordDto;
import info.model.token.TokenDto;
import info.service.InfoUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/info/update/")
@RequiredArgsConstructor
public class InfoUpdateController {

    private final InfoUpdateService service;

    @PutMapping("password")
    public ResponseEntity<TokenDto> setPassword(
            @Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        return service.updatePassword(updatePasswordDto);
    }

    @PutMapping("email")
    public ResponseEntity<TokenDto> setEmail(
            @Valid @RequestBody UpdateEmailDto updateEmailDto) {
        return service.updateEmail(updateEmailDto);
    }

    @PutMapping("name")
    public ResponseEntity<InfoDto> setNameSurname(
            @Valid @RequestBody UpdateNameSurnameDto updateNameSurnameDto) {
        return service.updateName(updateNameSurnameDto);
    }

    @PutMapping("photo")
    public ResponseEntity<InfoDto> setProfilePhoto(
            @RequestParam(value = "photoId") UUID photoId, @RequestParam("userId") Long userId) {
        return service.uploadProfilePhoto(photoId, userId);
    }
}
