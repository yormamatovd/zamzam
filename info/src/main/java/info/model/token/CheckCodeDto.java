package info.model.token;

import info.annotation.OtpCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckCodeDto {
    @NotBlank(message = "Token is mandatory")
    private String token;
    @OtpCode
    private String code;
}
