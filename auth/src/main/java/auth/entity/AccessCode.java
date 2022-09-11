package auth.entity;

import auth.entity.abs.AbsMain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessCode extends AbsMain {

    @Column(updatable = false)
    private Long userId;

    @Column(updatable = false)
    private String code;
}
