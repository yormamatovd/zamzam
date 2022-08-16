package seller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seller.model.InfoDto;
import seller.model.RegisterUserDto;
import seller.model.SellerDto;

import java.util.List;

@FeignClient("INFO")
public interface InfoTemplate {
    @PostMapping("local/info/create")
    ResponseEntity<InfoDto> createInfo(@RequestBody RegisterUserDto registerUserDto);

}
