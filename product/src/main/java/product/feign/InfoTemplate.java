package product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import product.model.InfoDto;

@FeignClient("INFO")
public interface InfoTemplate {

    @GetMapping("local/info/seller-info")
    ResponseEntity<InfoDto> getSellerInfo(@RequestParam(name = "infoId") Long id);
}
