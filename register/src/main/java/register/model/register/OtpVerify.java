package register.model.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import register.annotations.OtpCode;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerify {
    @NotBlank(message = "Token is mandatory")
    private String token;
    @OtpCode
    private String code;
}
