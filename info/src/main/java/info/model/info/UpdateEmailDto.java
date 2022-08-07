package info.model.info;

import info.annotation.Gmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmailDto {
    @Min(value = 1, message = "User ID is mandatory")
    private Long userId;
    @Gmail
    private String newEmail;
}
