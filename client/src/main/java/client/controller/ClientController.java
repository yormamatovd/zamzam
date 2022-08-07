package client.controller;

import client.model.ClientDto;
import client.model.register.RegisterUserDto;
import client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @GetMapping
    public ResponseEntity<ClientDto> getClient(@RequestParam(name = "clientId") Long id){
        return service.getClient(id);
    }
}
