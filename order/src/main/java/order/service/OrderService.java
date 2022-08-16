package order.service;

import order.model.OrderDto;
import order.model.OrderRegDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<OrderDto> makeOrder(OrderRegDto dto);


    ResponseEntity<List<OrderDto>> getClientOrders(Integer page, Long sinceDateTime, Long untilDateTime);
    ResponseEntity<OrderDto> cancelOrder(Long orderId);


}
