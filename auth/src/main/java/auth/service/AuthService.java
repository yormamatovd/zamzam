package auth.service;

import auth.model.InfoDto;
import auth.model.LoginDto;
import auth.model.token.TokenDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<TokenDto> login(LoginDto dto);

    InfoDto checkToken(String authorization);
}
