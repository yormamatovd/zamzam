package order.controller;

import lombok.RequiredArgsConstructor;
import order.enums.OrderStatus;
import order.model.GetByDates;
import order.model.OrderDto;
import order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("order/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/history")
    public ResponseEntity<List<OrderDto>> getHistory(@Valid @RequestBody GetByDates getByDates){
        return service.byStatus(List.of(OrderStatus.RECEIVED,OrderStatus.REJECTED,OrderStatus.CANCELED),getByDates);
    }
    @GetMapping("/rejected")
    public ResponseEntity<List<OrderDto>> rejected(@RequestBody GetByDates getByDates) {
        return service.byStatus(List.of(OrderStatus.REJECTED),getByDates);
    }
    @GetMapping("/received")
    public ResponseEntity<List<OrderDto>> getReceivedOrders(@Valid @RequestBody GetByDates getByDates) {
        return service.byStatus(List.of(OrderStatus.RECEIVED),getByDates);
    }
    @GetMapping("/delivered")
    public ResponseEntity<List<OrderDto>> delivered(@RequestBody GetByDates getByDates) {
        return service.byStatus(List.of(OrderStatus.DELIVERED),getByDates);
    }
}
