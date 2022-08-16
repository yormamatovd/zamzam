package info.service;

import info.model.ClientDto;
import info.model.SellerDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InfoService {

    ResponseEntity<Boolean> existByEmail(String email);

    ResponseEntity<ClientDto> getClientInfo(Long id);

    ResponseEntity<SellerDto> getSellerInfo(Long id);

    ResponseEntity<List<SellerDto>> getSellersInfo(Integer page);

}
