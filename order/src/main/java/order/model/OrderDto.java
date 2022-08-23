package order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long productId;

    private Long clientId;

    private Integer count;

    private Integer bottles;

    private String receivedTime;

    private String deliveredTime;

    private String orderedTime;

    private String estimateDeliveryTime;

    private String status;

    private String reason;

    private String sum;

    private String paid;

}
