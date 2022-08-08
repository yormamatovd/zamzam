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

    private ProductDto product;

    private ClientDto client;

    private Integer count;

    private boolean acceptedBySeller;

    private Integer returnedBottlesAmount;

    private Long clientReceivedDateTime;

    private Long orderedDateTime;

    private Long estimatedDeliveryDateTime;

    private Long sellerAcceptedDateTime;

    private String status;
}