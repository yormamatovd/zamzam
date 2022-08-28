package auth.service;

import auth.enums.ApiStatus;
import auth.exceptions.BadCredentialsException;
import auth.exceptions.NotFoundException;
import auth.exceptions.SystemException;
import auth.feign.InfoTemplate;
import auth.helper.Helper;
import auth.jwt.JWTProvider;
import auth.model.InfoDto;
import auth.model.LoginDto;
import auth.model.token.LoginTokenDto;
import auth.model.token.TokenDto;
import auth.model.token.TokenInfoDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final InfoTemplate infoTemplate;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.access-token-expire-time-minutes}")
    private Long accessExpiration;
    @Value("${jwt.refresh-token-expire-time-minutes}")
    private Long refreshExpiration;

    @Override
    public ResponseEntity<LoginTokenDto> login(LoginDto dto) {

        ResponseEntity<String> response;
        try {
            response = infoTemplate.getMyPassword(dto.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        if (response == null) throw new NotFoundException(ApiStatus.USER_NOT_FOUND);
        if (response.getStatusCodeValue() != 200 || response.getBody() == null)
            throw new NotFoundException(ApiStatus.USER_NOT_FOUND);

        if (passwordEncoder.matches(dto.getPassword(), response.getBody())) {

            TokenInfoDto accessTokenInfo = new TokenInfoDto(Helper.currentSeconds(), Helper.currentSeconds() + accessExpiration * 60, accessExpiration * 60);

            TokenInfoDto refreshTokenInfo = new TokenInfoDto(Helper.currentSeconds(), Helper.currentSeconds() + refreshExpiration * 60, refreshExpiration * 60);

            TokenDto accessToken = new TokenDto(jwtProvider.generateToken(dto.getUsername(), new Date(Helper.currentSeconds() * 1000 + accessExpiration * 60 * 1000)), accessTokenInfo);
            TokenDto refreshToken = new TokenDto(jwtProvider.generateToken(dto.getUsername(), new Date(Helper.currentSeconds() * 1000 + refreshExpiration * 60 * 1000)), refreshTokenInfo);
            return ResponseEntity.ok(new LoginTokenDto(accessToken, refreshToken));
        } else throw new BadCredentialsException(ApiStatus.WRONG_PASSWORD);
    }

    @Override
    public ResponseEntity<TokenDto> refreshAccessToken(String refreshToken) {
        String username = jwtProvider.getSubjectFromToken(refreshToken);
        if (username != null) {
            try {
                infoTemplate.getMyId(username).getBody();
                return ResponseEntity.ok(new TokenDto(
                        jwtProvider.generateToken(username,
                                new Date(Helper.currentSeconds() * 1000 + accessExpiration * 60 * 1000)),
                        new TokenInfoDto(Helper.currentSeconds(),
                                Helper.currentSeconds() + accessExpiration * 60,
                                accessExpiration * 60)
                ));
            } catch (Exception e) {
                throw new BadCredentialsException(ApiStatus.INVALID_TOKEN);
            }
        }
        throw new BadCredentialsException(ApiStatus.INVALID_TOKEN);
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
