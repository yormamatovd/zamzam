package product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.entity.abs.AbsMain;
import product.enums.ProductType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends AbsMain {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(nullable = false)
    private Double capacity;    //ml sig`imi

    @Column(nullable = false)
    private Double weight;      //grams og`irligi

    @Column(nullable = false)
    private UUID photoId;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, updatable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Long price; //so`m
}
