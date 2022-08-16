package order.feign;

import order.model.ClientDto;
import order.model.SellerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INFO")
public interface InfoTemplate {
    @GetMapping("info/client")
    ResponseEntity<ClientDto> getClientInfo(@RequestParam(name = "clientId") Long id);

    @GetMapping("info/seller")
    ResponseEntity<SellerDto> getSellerInfo(@RequestParam(name = "sellerId") Long id);
}
