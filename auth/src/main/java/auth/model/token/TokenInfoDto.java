package auth.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoDto {
    private Long createTimeSeconds;
    private Long expireTimeSeconds;
    private Long lifetimeSeconds;
}
