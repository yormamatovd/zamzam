package register.model.register;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import register.annotations.Gmail;
import register.annotations.Name;
import register.annotations.Password;
import register.annotations.Phone;
import register.enums.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @Gmail
    private String email;

    @Phone
    private String phone;

    @Password
    private String password;

    @Name
    private String name;
    @Name(message = "Surname(min_length=2,max_length=25, ALPHABETIC) is mandatory")
    private String surname;

    private UserType type;
}
