package seller.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import seller.entity.Seller;
import seller.enums.ApiStatus;
import seller.exceptions.NotFoundException;
import seller.exceptions.SystemException;
import seller.feign.ClientTemplate;
import seller.feign.InfoTemplate;
import seller.model.InfoDto;
import seller.model.SellerDto;
import seller.repository.SellerRepo;
import seller.service.SellerLocalService;

@Service
@RequiredArgsConstructor
public class SellerLocalServiceImpl implements SellerLocalService {

    private final InfoTemplate infoTemplate;
    private final ClientTemplate clientTemplate;
    private final SellerRepo sellerRepo;


    @Override
    public ResponseEntity<SellerDto> create(Long infoId) {
        try {
            ResponseEntity<InfoDto> response = infoTemplate.getInfo(infoId);
            InfoDto infoDto = response.getBody();
            assert infoDto != null;
            sellerRepo.save(new Seller(infoDto.getId()));
            return ResponseEntity.ok(new SellerDto(infoDto));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) throw new NotFoundException(ApiStatus.INFO_NOT_FOUND);
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
    }
}
