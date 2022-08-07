package seller.service;

import org.springframework.http.ResponseEntity;
import seller.model.SellerDto;

import java.util.List;

public interface SellerService {
    ResponseEntity<SellerDto> getSeller(Long id);

    ResponseEntity<List<SellerDto>> getSellers(Integer page);
}
