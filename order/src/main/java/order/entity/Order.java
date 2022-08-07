package order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import order.entity.abs.AbsMain;
import order.enums.OrderStatus;
import order.helper.Helper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends AbsMain {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private Integer count;

    private boolean acceptedBySeller;

    @Column(nullable = false)
    private Integer returnedBottlesAmount=0;

    private Long clientReceivedDateTime;

    @Column(nullable = false,updatable = false)
    private Long orderedDateTime= Helper.currentSeconds();

    private Long estimatedDeliveryDateTime;

    private Long sellerAcceptedDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}
