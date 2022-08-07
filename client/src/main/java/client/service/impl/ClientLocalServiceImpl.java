package client.service.impl;

import client.entity.Client;
import client.enums.ApiStatus;
import client.exceptions.NotFoundException;
import client.exceptions.SystemException;
import client.feign.InfoTemplate;
import client.model.ClientDto;
import client.model.InfoDto;
import client.model.register.RegisterUserDto;
import client.repo.ClientRepo;
import client.service.ClientLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientLocalServiceImpl implements ClientLocalService {

    private final ClientRepo repo;
    private final InfoTemplate infoTemplate;

    @Override
    public ResponseEntity<String> deleteClient(Long id) {
        Optional<Client> clientOptional = repo.findByInfoIdAndActiveTrue(id);
        if (clientOptional.isEmpty()) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);
        clientOptional.get().setActive(false);
        repo.save(clientOptional.get());
        return ResponseEntity.ok("OK");
    }

    @Override
    public ResponseEntity<ClientDto> create(RegisterUserDto registerUserDto) {
        try {
            ResponseEntity<InfoDto> response = infoTemplate.createInfo(registerUserDto);
            repo.save(new Client(Objects.requireNonNull(response.getBody()).getId()));
            return ResponseEntity.ok(new ClientDto(response.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
    }
}
