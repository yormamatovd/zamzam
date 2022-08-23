package seller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seller.annotations.Gmail;
import seller.annotations.Name;
import seller.annotations.Phone;
import seller.enums.UserType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @Gmail
    private String email;

    @Phone
    private String phone;

//    @Password //tayyor encrypt password keladi
    private String password;

    @Name
    private String name;
    @Name(message = "Surname(min_length=2,max_length=25, ALPHABETIC) is mandatory")
    private String surname;

    private UserType type;
}
