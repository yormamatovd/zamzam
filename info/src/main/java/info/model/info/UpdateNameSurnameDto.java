package info.model.info;

import info.annotation.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNameSurnameDto {
    @Min(value = 1, message = "User ID is mandatory")
    private Long userId;
    @Name
    private String name;
    @Name(message = "Surname(min_length=2,max_length=25, ALPHABETIC) is mandatory")
    private String surname;
}
