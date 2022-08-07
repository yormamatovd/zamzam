package client.entity.abs;

import client.helper.Helper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbsMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long createdAt;

    @Column(nullable = false)
    private Long updatedAt;

    @Column(updatable = false)
    @CreatedBy
    private Long createdById;

    @LastModifiedBy
    private Long updatedById;

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
