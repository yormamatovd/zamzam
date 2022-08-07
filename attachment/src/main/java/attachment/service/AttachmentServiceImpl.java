package attachment.service;

import attachment.entity.Attachment;
import attachment.enums.ApiStatus;
import attachment.exceptions.NotFoundException;
import attachment.exceptions.UnsupportedMediaTypeException;
import attachment.helper.Helper;
import attachment.model.AttachmentDto;
import attachment.repo.AttachmentRepo;
import attachment.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepo attachmentRepo;

    private Attachment savePhotoAsMultipart(MultipartFile multipart) {
        try {
            if (multipart.getContentType() == null) return null;

            if (!multipart.getContentType().equals("image/png") &&
                    !multipart.getContentType().equals("image/jpg") &&
                    !multipart.getContentType().equals("image/jpeg"))
                return null;


            String name = Objects.requireNonNull(multipart.getOriginalFilename()).substring(0, multipart.getOriginalFilename().lastIndexOf("."));
            String format = multipart.getOriginalFilename().substring(multipart.getOriginalFilename().lastIndexOf("."));
            if (name.length() >= 100) name = name.substring(0, 94);

            Attachment attachment = new Attachment();
            attachment.setName(name);
            attachment.setFormat(format);
            attachment.setFullName(multipart.getOriginalFilename().length() >= 110 ? name + format : multipart.getOriginalFilename());
            attachment.setSource(multipart.getBytes());
            attachment.setContentType(multipart.getContentType());
            attachment.setSize(multipart.getSize());
            attachment.setCreatedById(Session.getInfoId());
            attachment.setUpdatedById(Session.getInfoId());
            attachment.setCreatedAt(Helper.currentSeconds());

            return attachmentRepo.save(attachment);

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ResponseEntity<byte[]> getPhoto(UUID id) {

        Optional<Attachment> attachmentOptional = attachmentRepo.findByIdAndActiveTrue(id);
        if (attachmentOptional.isEmpty()) throw new NotFoundException(ApiStatus.FILE_NOT_FOUND);

        Attachment attachment = attachmentOptional.get();

        if (attachment.getContentType().equals("image/jpg") || attachment.getContentType().equals("image/jpeg"))
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(attachment.getSource());
        else
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(attachment.getSource());
    }

    @Override
    public ResponseEntity<String> uploadPhoto(MultipartFile multipart) {
        Attachment attachment = savePhotoAsMultipart(multipart);
        if (attachment == null) throw new UnsupportedMediaTypeException(ApiStatus.UNSUPPORTED_MEDIA_TYPE);
        return ResponseEntity.ok(attachment.getId().toString());
    }

    @Override
    public ResponseEntity<AttachmentDto> getPhotoDetails(UUID id) {
        Optional<Attachment> attachmentOptional = attachmentRepo.findByIdAndActiveTrue(id);
        if (attachmentOptional.isPresent()) {
            Attachment attachment = attachmentOptional.get();
            return ResponseEntity.ok(
                    new AttachmentDto(
                            attachment.getId(),
                            attachment.getContentType(),
                            attachment.getSize()
                    )
            );
        } else throw new NotFoundException(ApiStatus.FILE_NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> delete(UUID id) {
        Optional<Attachment> attachmentOptional = attachmentRepo.findById(id);
        if (attachmentOptional.isPresent()) {
            attachmentOptional.get().setActive(false);
            attachmentRepo.save(attachmentOptional.get());
            return ResponseEntity.ok(ApiStatus.Ok.name());
        } else throw new NotFoundException(ApiStatus.FILE_NOT_FOUND);
    }
}
