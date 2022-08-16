package order.repository;

import order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

    Optional<Order> findByIdAndActiveTrue(Long orderId);


    @Query(value = "select * from orders since_date_time>=?1 and until_date_time<=?2 and client_id=?3 and active=true",nativeQuery = true)
    Page<Order> getClientActiveOrders(Long sinceDateTime, Long untilDateTime,Long clientId, Pageable pageable);

    @Query(value = "select * from orders since_date_time>=?1 and until_date_time<=?2 and seller_id=?3 and active=true",nativeQuery = true)
    Page<Order> getSellerActiveOrders(Long sinceDateTime, Long untilDateTime,Long sellerId, Pageable pageable);
}
