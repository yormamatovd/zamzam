package systemuser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import systemuser.model.ClientDto;
import systemuser.model.InfoDto;

@FeignClient("INFO")
public interface InfoTemplate {

    @GetMapping("local/info/client-info")
    ResponseEntity<ClientDto> getClient(@RequestParam(name = "infoId") Long clientId);

    @PutMapping("local/info/to-seller")
    ResponseEntity<InfoDto> toSeller(@RequestParam(name = "infoId") Long id);
}
