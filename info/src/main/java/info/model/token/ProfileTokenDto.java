package info.model.token;

import info.enums.TokenActionType;
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
    private TokenActionType actionType;
    private TokenInfoDto tokenInfo;
    private String verifyCode;
}
