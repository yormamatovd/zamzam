package info.service;

import info.model.RegisterUserDto;
import info.model.info.InfoDto;
import info.model.info.UpdateEmailDto;
import info.model.info.UpdateNameSurnameDto;
import info.model.info.UpdatePasswordDto;
import info.model.token.CheckCodeDto;
import info.model.token.TokenDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface InfoService {

    ResponseEntity<Boolean> existByEmail(String email);


}
