package order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import order.entity.abs.AbsMain;
import order.enums.OrderStatus;
import order.enums.RejectReasons;
import order.helper.Helper;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends AbsMain {

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long clientId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Integer returnedBottlesAmount=0;

    private Long clientReceivedDateTime=0L;

    @Column(nullable = false,updatable = false)
    private Long orderedDateTime= Helper.currentSeconds();

    private Long estimatedDeliveryDateTime=0L;

    private Long deliveredDateTime=0L;

    private Long sellerAcceptedDateTime=0L;

    private Long rejectedDateTime=0L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private Long paidAmount=0L;

    @Enumerated(EnumType.STRING)
    private RejectReasons reason;
}
