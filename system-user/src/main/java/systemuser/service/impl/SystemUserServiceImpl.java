package systemuser.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import systemuser.enums.ApiStatus;
import systemuser.exception.NotFoundException;
import systemuser.exception.SystemException;
import systemuser.feign.ClientTemplate;
import systemuser.feign.InfoTemplate;
import systemuser.feign.SellerTemplate;
import systemuser.model.ClientDto;
import systemuser.model.SellerDto;
import systemuser.service.SystemUserService;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {

    private final InfoTemplate infoTemplate;
    private final ClientTemplate clientTemplate;
    private final SellerTemplate sellerTemplate;

    @Override
    public ResponseEntity<SellerDto> makeSeller(Long id) {
        try {
            ResponseEntity<ClientDto> response = infoTemplate.getClient(id);
            ClientDto clientDto = response.getBody();
            assert clientDto != null;
            clientTemplate.deleteClient(clientDto.getInfo().getId());
            clientDto.setInfo(infoTemplate.toSeller(clientDto.getInfo().getId()).getBody());
            return ResponseEntity.ok(sellerTemplate.createSeller(clientDto.getInfo().getId()).getBody());
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);
            else throw new SystemException(ApiStatus.SERVER_ERROR);
        }
    }
}
