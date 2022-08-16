package client.feign;

import client.model.InfoDto;
import client.model.register.RegisterUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("INFO")
public interface InfoTemplate {

    @PostMapping("local/info/create")
    ResponseEntity<InfoDto> createInfo(@RequestBody RegisterUserDto registerUserDto);

    @GetMapping("local/info")
    ResponseEntity<InfoDto> getInfo(@RequestParam(name = "infoId") Long infoId);

}
