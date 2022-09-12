package register.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import register.model.MailCodeDto;

@FeignClient("EMAIL")
public interface EmailTemplate {
    @PostMapping("/send/code")
    Boolean sendCodeMail(@RequestBody MailCodeDto dto);
}
