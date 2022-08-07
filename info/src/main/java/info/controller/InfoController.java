package info.controller;

import info.model.RegisterUserDto;
import info.model.info.InfoDto;
import info.model.info.UpdateEmailDto;
import info.model.info.UpdateNameSurnameDto;
import info.model.info.UpdatePasswordDto;
import info.model.token.TokenDto;
import info.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {

    private final InfoService service;

    @PostMapping("/exist-email")
    public ResponseEntity<Boolean> existEmail(@RequestParam(name = "email") String email) {
        return service.existByEmail(email);
    }


}
