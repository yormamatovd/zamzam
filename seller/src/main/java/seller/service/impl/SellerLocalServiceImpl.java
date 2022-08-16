package seller.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import seller.entity.Seller;
import seller.enums.ApiStatus;
import seller.exceptions.SystemException;
import seller.feign.InfoTemplate;
import seller.model.InfoDto;
import seller.model.RegisterUserDto;
import seller.model.SellerDto;
import seller.repository.SellerRepo;
import seller.service.SellerLocalService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SellerLocalServiceImpl implements SellerLocalService {

    private final InfoTemplate infoTemplate;
    private final SellerRepo sellerRepo;


    @Override
    public ResponseEntity<SellerDto> create(RegisterUserDto registerUserDto) {
        try {
            ResponseEntity<InfoDto> response = infoTemplate.createInfo(registerUserDto);
            sellerRepo.save(new Seller(Objects.requireNonNull(response.getBody()).getId()));
            return ResponseEntity.ok(new SellerDto(response.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
    }
}
