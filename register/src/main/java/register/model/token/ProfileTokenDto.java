package register.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileTokenDto {
    private String body;
    private TokenInfoDto tokenInfo;
    private String verifyCode;
}
