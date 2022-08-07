package client.service;

import client.model.ClientDto;
import client.model.register.RegisterUserDto;
import org.springframework.http.ResponseEntity;

public interface ClientLocalService {
    ResponseEntity<String> deleteClient(Long id);

    ResponseEntity<ClientDto> create(RegisterUserDto registerUserDto);
}
