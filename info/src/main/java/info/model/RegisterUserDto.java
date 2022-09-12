package info.model;

import info.annotation.Gmail;
import info.annotation.Name;
import info.annotation.Phone;
import info.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @Gmail
    private String email;

    @Phone
    private String phone;

//    @Password   //parol encodlangan holatda keladi
    private String password;

    @Name
    private String name;
    @Name(message = "Surname(min_length=2,max_length=25, ALPHABETIC) is mandatory")
    private String surname;

    private UserType type;
}
