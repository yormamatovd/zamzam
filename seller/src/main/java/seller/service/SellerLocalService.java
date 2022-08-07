package seller.service;

import org.springframework.http.ResponseEntity;
import seller.model.SellerDto;

public interface SellerLocalService {
    ResponseEntity<SellerDto> create(Long infoId);
}
