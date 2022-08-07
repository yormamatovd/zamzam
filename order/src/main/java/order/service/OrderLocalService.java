package order.service;

import order.model.OrderDto;
import order.model.OrderRegDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderLocalService {
    ResponseEntity<List<OrderDto>> getSellerOrders(Long sellerId, Integer page, Long sinceDateTime, Long untilDateTime);

    ResponseEntity<List<OrderDto>> getClientOrders(Long clientId, Integer page, Long sinceDateTime, Long untilDateTime);

    ResponseEntity<OrderDto> acceptOrder(Long orderId,Long sellerId);

    ResponseEntity<OrderDto> setEstimated(Long dateTimeSeconds);

    ResponseEntity<OrderDto> makeOrder(OrderRegDto dto);

    ResponseEntity<OrderDto> cancelOrder(Long orderId, Long clientId);
}
