package order.service;

import order.model.OrderDto;
import order.model.OrderRegDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<OrderDto> makeOrder(OrderRegDto dto);
}
