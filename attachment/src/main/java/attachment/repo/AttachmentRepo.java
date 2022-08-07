package attachment.repo;

import attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findByIdAndActiveTrue(UUID id);
}
