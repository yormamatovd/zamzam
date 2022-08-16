package product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import product.model.InfoDto;

@FeignClient("INFO")
public interface InfoTemplate {

    @GetMapping("/info/seller")
    ResponseEntity<InfoDto> getSellerInfo(@RequestParam(name = "sellerId") Long id);
}
