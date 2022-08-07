package systemuser.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("CLIENT")
public interface ClientTemplate {

    @DeleteMapping("local/client/delete")
    ResponseEntity<String> deleteClient(@RequestParam(name = "infoId") Long id);
}
