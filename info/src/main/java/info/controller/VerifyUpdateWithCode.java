package info.controller;

import info.model.token.CheckCodeDto;
import info.service.UpdateVerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/verify-update")
@RequiredArgsConstructor
public class VerifyUpdateWithCode {
    private final UpdateVerifyService service;

    @PostMapping("/password")
    public ResponseEntity<String> setPassword(
            @Valid @RequestBody CheckCodeDto dto) {
        return service.updatePasswordVerify(dto);
    }
}
