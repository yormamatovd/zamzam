package info.model.info;

import info.annotation.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    @Min(value = 1, message = "User ID is mandatory")
    private Long userId;
    @Password
    private String newPassword;
}
