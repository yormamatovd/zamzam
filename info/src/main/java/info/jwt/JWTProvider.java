package info.jwt;

import info.enums.ApiStatus;
import info.exception.BadRequestException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTProvider {

    @Value("${jwt.custom-secret-key}")
    private String customKey;

    public String generateCustomToken(String subject) {
        return Jwts
                .builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, customKey)
                .compact();
    }

    public String getSubjectFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(customKey)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            throw new BadRequestException(ApiStatus.WRONG_TOKEN);
        }
    }
}
