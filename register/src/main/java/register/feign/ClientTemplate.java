package register.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import register.model.ClientDto;
import register.model.register.RegisterUserDto;

@Component
@FeignClient("CLIENT")
public interface ClientTemplate {

    @PostMapping("local/client/create")
    ResponseEntity<ClientDto> createClient(@RequestBody RegisterUserDto registerUserDto);
}
