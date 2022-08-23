package product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private String type;

    private Double capacity;// ml

    private Double weight; //gramm

    private String photoId;

    private String description;

    private boolean active;

    private Long sellerId;

    private Long price;
}
