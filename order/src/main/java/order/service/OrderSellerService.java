package order.service;

import order.model.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderSellerService {

    ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(Integer page);

    ResponseEntity<List<OrderDto>> getSellerOrders(Integer page, String[] dates,String by);

    ResponseEntity<OrderDto> acceptOrder(Long orderId);

    ResponseEntity<OrderDto> setEstimated(Long dateTimeSeconds, Long orderId);

    ResponseEntity<String> reject(String reason, Long orderId);

    ResponseEntity<List<OrderDto>> delivered(Integer page, String[] dates);

    ResponseEntity<List<OrderDto>> rejected(Integer page, String[] dates);
}
