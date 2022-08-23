package order.repository;

import order.entity.Order;
import order.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndActiveTrue(Long orderId);


    Page<Order> findAllByClientIdAndCreatedAtBetween(Long clientId, Long startTime, Long endTime, Pageable pageable);

    Page<Order> findAllBySellerIdAndCreatedAtBetween(Long sellerId, Long startTime, Long endTime, Pageable pageable);

    Page<Order> findAllBySellerIdAndEstimatedDeliveryDateTimeBetweenAndActiveTrue(
            Long sellerId, Long startTime, Long endTime, Pageable pageable);


    Page<Order> findAllByClientIdAndStatusAndActiveTrue(Long clientId, OrderStatus status, Pageable pageable);

    Page<Order> findAllBySellerIdAndStatusAndActiveTrue(Long sellerId, OrderStatus status, Pageable pageable);

    Page<Order> findAllByStatusAndSellerIdAndDeliveredDateTimeBetweenAndActiveTrue(
            OrderStatus status, Long sellerId, Long startTime, Long endTime, Pageable pageable);

    Page<Order> findAllByStatusAndSellerIdAndRejectedDateTimeBetweenAndActiveTrue(
            OrderStatus status, Long sellerId, Long startTime, Long endTime, Pageable pageable);

    @Query(value = "select * from orders where client_id=?1 and status!=?2 and created_at>=?3 and created_at<=?4", nativeQuery = true)
    Page<Order> findClientNotEqualsStatusOrdersByDatesActiveTrue(
            Long clientId, String status, Long startTime, Long endTime, Pageable pageable);
    @Query(value = "select * from orders where client_id=?1 and status!=?2", nativeQuery = true)
    Page<Order> findClientNotEqualsStatusOrdersActiveTrue(
            Long clientId, String status, Pageable pageable);

    Page<Order> findAllBySellerId(Long sellerId, Pageable pageable);

    Page<Order> findAllByClientId(Long clientId, Pageable pageable);


    Page<Order> findAllByStatusAndClientIdAndActiveTrueAndRejectedDateTimeBetween(OrderStatus status, Long clientId, Long rejectedDateTime, Long rejectedDateTime2, Pageable pageable);

}
