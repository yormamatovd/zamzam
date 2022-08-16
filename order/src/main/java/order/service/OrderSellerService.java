package order.service;

import order.model.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderSellerService {

    ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(Integer page);

    ResponseEntity<List<OrderDto>> getSellerOrders(Integer page, Long sinceDateTime, Long untilDateTime);

    ResponseEntity<OrderDto> acceptOrder(Long orderId);

    ResponseEntity<OrderDto> setEstimated(Long dateTimeSeconds, Long orderId);

}
