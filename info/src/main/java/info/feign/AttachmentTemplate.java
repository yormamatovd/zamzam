package info.feign;

import info.model.AttachmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient("ATTACHMENT")
public interface AttachmentTemplate {

    @GetMapping("/file/detail")
    ResponseEntity<AttachmentDto> detail(@RequestParam("id") UUID id);

    @DeleteMapping("/file/delete")
    ResponseEntity<String> delete(@RequestParam("id") UUID id);
}
