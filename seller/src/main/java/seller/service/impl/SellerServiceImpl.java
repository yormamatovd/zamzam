package seller.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import seller.enums.ApiStatus;
import seller.exceptions.NotFoundException;
import seller.exceptions.SystemException;
import seller.feign.InfoTemplate;
import seller.model.InfoDto;
import seller.model.SellerDto;
import seller.service.SellerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final InfoTemplate infoTemplate;

    @Override
    public ResponseEntity<SellerDto> getSeller(Long id) {
        try {
            ResponseEntity<InfoDto> infoResponse = infoTemplate.getSellerInfo(id);
            return ResponseEntity.ok(new SellerDto(infoResponse.getBody()));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<SellerDto>> getSellers(Integer page) {
        try {
            ResponseEntity<List<InfoDto>> infoResponse = infoTemplate.getSellersInfo(page);
            List<InfoDto> body = infoResponse.getBody();
            assert body != null;
            return ResponseEntity.ok(body.stream().map(SellerDto::new).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
    }
}
