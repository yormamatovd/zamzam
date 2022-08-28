package auth.service;

import auth.model.InfoDto;
import auth.model.LoginDto;
import auth.model.token.LoginTokenDto;
import auth.model.token.TokenDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<LoginTokenDto> login(LoginDto dto);

    InfoDto checkToken(String authorization);

    ResponseEntity<TokenDto> refreshAccessToken(String refreshToken);
}
