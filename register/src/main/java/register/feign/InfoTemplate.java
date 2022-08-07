package register.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import register.model.InfoDto;
import register.model.register.RegisterUserDto;

@FeignClient("INFO")
public interface InfoTemplate {

    @PostMapping("/info/exist-email")
    ResponseEntity<Boolean> existEmail(@RequestParam(name = "email") String email);
}
