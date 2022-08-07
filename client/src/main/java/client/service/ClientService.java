package client.service;

import client.model.ClientDto;
import client.model.register.RegisterUserDto;
import org.springframework.http.ResponseEntity;

public interface ClientService {

    ResponseEntity<ClientDto> getClient(Long id);
}
