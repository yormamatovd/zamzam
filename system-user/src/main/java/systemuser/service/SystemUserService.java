package systemuser.service;

import org.springframework.http.ResponseEntity;
import systemuser.model.SellerDto;

public interface SystemUserService {
    ResponseEntity<SellerDto> makeSeller(Long id);
}
