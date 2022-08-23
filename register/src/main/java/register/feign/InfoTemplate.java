package register.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INFO")
public interface InfoTemplate {

    @PostMapping("/info/exist-email")
    ResponseEntity<Boolean> existEmail(@RequestParam(name = "email") String email);

    @PostMapping("/info/exist-phone")
    ResponseEntity<Boolean> existPhone(@RequestParam(name = "phone") String email);
}
