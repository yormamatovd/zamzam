package auth.model;

import auth.annotations.Password;
import auth.annotations.Username;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Username
    private String username;

    @Password
    private String password;
}
