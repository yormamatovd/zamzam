package order.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetByDates {

    private Integer page;

    private String[] dates;

    public GetByDates(Integer page, String[] dates) {
        this.page = (page == null ? 0 : page);
        this.dates = dates;
    }
}
