package auth.controller;

import auth.model.InfoDto;
import auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("local/auth")
@RequiredArgsConstructor
public class AuthLocalController {

    private final AuthService authService;

    @PostMapping("/check-token")
    public InfoDto checkToken(@RequestHeader("Authorization") String authorization) {
        return authService.checkToken(authorization);
    }
}
