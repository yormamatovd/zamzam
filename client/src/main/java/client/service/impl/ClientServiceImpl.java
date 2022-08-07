package client.service.impl;

import client.enums.ApiStatus;
import client.exceptions.NotFoundException;
import client.exceptions.SystemException;
import client.feign.InfoTemplate;
import client.model.ClientDto;
import client.model.InfoDto;
import client.service.ClientService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final InfoTemplate infoTemplate;

    @Override
    public ResponseEntity<ClientDto> getClient(Long id) {
        try {
            ResponseEntity<InfoDto> info = infoTemplate.getClientInfo(id);
            return ResponseEntity.ok(new ClientDto(info.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);
        }
        throw new SystemException(ApiStatus.SERVER_ERROR);
    }


}
