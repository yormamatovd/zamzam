package client.controller;

import client.model.ClientDto;
import client.model.register.RegisterUserDto;
import client.service.ClientLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/local/client")
@RequiredArgsConstructor
public class ClientLocalController {
    private final ClientLocalService service;

    @PostMapping("/create")
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return service.create(registerUserDto);
    }
}
