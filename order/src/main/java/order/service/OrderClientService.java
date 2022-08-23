package order.service;

import order.model.GetByDates;
import order.model.OrderDto;
import order.model.OrderRegDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderClientService {

    ResponseEntity<OrderDto> makeOrder(OrderRegDto dto);

    ResponseEntity<List<OrderDto>> getClientOrders(GetByDates getByDates);

    ResponseEntity<OrderDto> cancelOrder(Long orderId);

    ResponseEntity<List<OrderDto>> getNotReceivedOrders(GetByDates getByDates);

    ResponseEntity<List<OrderDto>> received(GetByDates getByDates);

    ResponseEntity<List<OrderDto>> rejected(GetByDates getByDates);
}
