package register.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import register.model.SellerDto;
import register.model.register.RegisterUserDto;

@FeignClient("SELLER")
public interface SellerTemplate {
    @PostMapping("local/seller/create")
    ResponseEntity<SellerDto> createSeller(@RequestBody RegisterUserDto registerUserDto);

}
