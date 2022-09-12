package client.model.register;

import client.annotations.Gmail;
import client.annotations.Name;
import client.annotations.Phone;
import client.enums.UserType;
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
