package auth.service;

import auth.enums.ApiStatus;
import auth.exceptions.BadCredentialsException;
import auth.exceptions.NotFoundException;
import auth.feign.InfoTemplate;
import auth.helper.Helper;
import auth.jwt.JWTProvider;
import auth.model.InfoDto;
import auth.model.LoginDto;
import auth.model.token.TokenDto;
import auth.model.token.TokenInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final InfoTemplate infoTemplate;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.expire-time-minutes}")
    private Long expiration;

    @Override
    public ResponseEntity<TokenDto> login(LoginDto dto) {

        ResponseEntity<String> response;
        try {
            response = infoTemplate.getMyPassword(dto.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException(ApiStatus.USER_NOT_FOUND);
        }
        if (response == null) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);
        if (response.getStatusCodeValue() != 200) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);
        if (response.getBody() == null) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);


        if (passwordEncoder.matches(dto.getPassword(), response.getBody())) {

            TokenInfoDto infoDto = new TokenInfoDto();
            infoDto.setCreateTimeSeconds(Helper.currentSeconds());
            infoDto.setExpireTimeSeconds(infoDto.getCreateTimeSeconds() + expiration * 60 * 1000);
            infoDto.setLifetimeSeconds(expiration * 60 * 1000);

            String token = jwtProvider.generateToken(dto.getUsername());
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setTokenInfo(infoDto);

            return ResponseEntity.ok(tokenDto);
        } else throw new BadCredentialsException(ApiStatus.WRONG_PASSWORD);


    }

    @Override
    public InfoDto checkToken(String authorization) {
        if (authorization.startsWith("Bearer ") && !authorization.contains("  ")) {
            String token = authorization.substring(7);

            String username = jwtProvider.getSubjectFromToken(token);

            if (username != null) {
                try {
                    return infoTemplate.getMyId(username).getBody();
                } catch (Exception e) {
                    throw new BadCredentialsException(ApiStatus.INVALID_TOKEN);
                }
            }
        }
        throw new BadCredentialsException(ApiStatus.INVALID_TOKEN);
    }
}
