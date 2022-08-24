package order.controller;

import lombok.RequiredArgsConstructor;
import order.model.GetByDates;
import order.model.OrderDto;
import order.service.OrderSellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order/seller")
@RequiredArgsConstructor
public class OrderSellerController {

    private final OrderSellerService service;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getSellerOrders(@RequestBody GetByDates getByDates) {
        return service.getSellerOrders(getByDates);
    }

    @GetMapping("/today-work")
    public ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return service.getSellerOrdersDeliveryToday(page);
    }


    @PutMapping("/accept")
    public ResponseEntity<OrderDto> acceptOrder(@RequestParam(name = "orderId") Long orderId) {
        return service.acceptOrder(orderId);
    }

    @PutMapping("/estimate-delivery")
    public ResponseEntity<OrderDto> setEstimatedDateTime(@RequestParam(name = "time") Long dateTimeSeconds,
                                                         @RequestParam(name = "orderId") Long orderId) {
        return service.setEstimated(dateTimeSeconds, orderId);
    }

    @PutMapping("/reject")
    public ResponseEntity<String> rejectOrder(@RequestParam(name = "reason") String reason,
                                                         @RequestParam(name = "orderId") Long orderId) {
        return service.reject(reason,orderId);
    }


}
