package order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRegDto {


    @NotNull
    private Long productId;

    @NotNull(message = "count is mandatory")
    @Min(message = "count min 1",value = 1)
    @Max(message = "count max 100",value = 100)
    private Integer count;
}
