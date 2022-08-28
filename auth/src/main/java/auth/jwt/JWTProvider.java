package auth.jwt;

import auth.helper.Helper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTProvider {

    @Value("${jwt.key}")
    private String authKey;

    public String generateToken(String subject, Date expiration) {
        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(Helper.currentDate())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, authKey)
                .compact();
    }

    public String getSubjectFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(authKey)
                    .parseClaimsJws(token)
                    .getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}
