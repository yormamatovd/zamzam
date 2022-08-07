package product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import product.anatations.DoubleValidator;
import product.anatations.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegDto {
    @NotBlank(message = "Product name is mandatory")
    @Size(min = 3, max = 100, message = "Product name(min=3,max=100)")
    private String name;

    @ProductType
    private String type;

    @DoubleValidator(message = "capacity is mandatory")
    private Double capacity;

    @DoubleValidator(message = "capacity is mandatory")
    private Double weight; //gramm

    @NotNull(message = "photoID is mandatory")
    private UUID photoId;

    @NotNull(message = "price is mandatory")
    private Long price;

    @Size(min = 0, max = 1000, message = "description max size 1000 character")
    private String description;
}
