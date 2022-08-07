package attachment.entity;

import attachment.helper.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(length = 100)
    private String name;

    private byte[] source;

    @Column(length = 50)
    private String contentType;

    @Column(length = 10)
    private String format;

    @Column(length = 110)
    private String fullName;

    private Long size;

    @Column(updatable = false, nullable = false)
    @CreatedBy
    private Long createdById;

    @Column(nullable = false)
    @LastModifiedBy
    private Long updatedById;

    @Column(nullable = false, updatable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    private boolean active = true;

    @PrePersist
    private void beforeInsert() {
        createdAt = Helper.currentSeconds();
        updatedAt = Helper.currentSeconds();
    }

    @PreUpdate
    private void beforeUpdate() {
        updatedAt = Helper.currentSeconds();
    }
}
