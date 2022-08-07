package attachment.service;

import attachment.model.AttachmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AttachmentService {
    ResponseEntity<byte[]> getPhoto(UUID id);

    ResponseEntity<String> uploadPhoto(MultipartFile multipart);

    ResponseEntity<AttachmentDto> getPhotoDetails(UUID id);

    ResponseEntity<String> delete(UUID id);

}
