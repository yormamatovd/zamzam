package order.service;

import order.enums.OrderStatus;
import order.model.GetByDates;
import order.model.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<List<OrderDto>> byStatus(List<OrderStatus> statuses, GetByDates getByDates);
}
