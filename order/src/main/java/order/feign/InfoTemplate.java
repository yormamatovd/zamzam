package order.feign;

import order.model.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INFO")
public interface InfoTemplate {
    @GetMapping("local/info/client-info")
    ResponseEntity<ClientDto> getClientInfo(@RequestParam(name = "infoId") Long id);
}
