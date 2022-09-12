package info.feign;

import info.model.MailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("EMAIL")
public interface EmailTemplate {
    @PostMapping("/send")
    Boolean sendMail(@RequestBody MailDto dto);
}
