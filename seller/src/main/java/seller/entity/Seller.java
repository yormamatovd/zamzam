package seller.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seller.entity.abs.AbsMain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seller extends AbsMain {
    @Column(nullable = false, updatable = false, unique = true)
    private Long infoId;
}
