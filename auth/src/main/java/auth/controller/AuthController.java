package auth.controller;

import auth.model.LoginDto;
import auth.model.token.LoginTokenDto;
import auth.model.token.TokenDto;
import auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginTokenDto> login(@Valid @RequestBody LoginDto dto) {
        return service.login(dto);
    }

    @PostMapping("/refresh-access")
    public ResponseEntity<TokenDto> refreshAccessToken(@RequestParam(name = "refreshToken") String refreshToken){
        return service.refreshAccessToken(refreshToken);
    }
    @PostMapping("/access-code")
    public ResponseEntity<Boolean> accessCode(@RequestParam Long id){
        return service.sendAccessCode();
    }
}
