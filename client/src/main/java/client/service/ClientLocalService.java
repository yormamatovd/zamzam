package client.service;

import client.model.ClientDto;
import client.model.register.RegisterUserDto;
import org.springframework.http.ResponseEntity;

public interface ClientLocalService {
    ResponseEntity<ClientDto> create(RegisterUserDto registerUserDto);
}
