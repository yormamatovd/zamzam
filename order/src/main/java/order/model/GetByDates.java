package order.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class GetByDates {

    @Min(value = 0, message = "Page min value 0")
    private Integer page = 0;

    private String[] dates;
}
