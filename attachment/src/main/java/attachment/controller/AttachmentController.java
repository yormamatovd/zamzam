package attachment.controller;

import attachment.model.AttachmentDto;
import attachment.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<byte[]> getPhotoFile(@RequestParam("id") UUID id) {
        return attachmentService.getPhoto(id);
    }

    @GetMapping("/detail")
    public ResponseEntity<AttachmentDto> getPhotoFileDetails(@RequestParam("id") UUID id) {
        return attachmentService.getPhotoDetails(id);
    }

    @PostMapping
    public ResponseEntity<String> uploadPhotoFile(@RequestParam(value = "photo") MultipartFile multipart) {
        return attachmentService.uploadPhoto(multipart);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("id") UUID id) {
        return attachmentService.delete(id);
    }
}
