package auth.feign;

import auth.model.InfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INFO")
public interface InfoTemplate {

    @GetMapping("local/info/my-id")
    ResponseEntity<InfoDto> getMyId(@RequestParam(name = "username") String username);

    @GetMapping("local/info/my-pass")
    ResponseEntity<String> getMyPassword(@RequestParam(name = "username") String username);
}
