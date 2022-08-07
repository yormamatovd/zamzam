package systemuser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import systemuser.model.SellerDto;

@FeignClient("SELLER")
public interface SellerTemplate {
    @PostMapping("local/seller/create")
    ResponseEntity<SellerDto> createSeller(@RequestParam(name = "infoId") Long infoId);
}
