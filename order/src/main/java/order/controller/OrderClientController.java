package order.controller;

import lombok.RequiredArgsConstructor;
import order.model.GetByDates;
import order.model.OrderDto;
import order.model.OrderRegDto;
import order.service.OrderClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("order/client")
@RequiredArgsConstructor
public class OrderClientController {

    private final OrderClientService service;

    @PostMapping
    public ResponseEntity<OrderDto> makeOrder(@Valid @RequestBody OrderRegDto dto) {
        return service.makeOrder(dto);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getClientOrders(@Valid @RequestBody GetByDates getByDates) {
        return service.getClientOrders(getByDates);
    }

    @PutMapping("/cancel")
    public ResponseEntity<OrderDto> cancelOrder(@RequestParam(name = "orderId") Long orderId) {
        return service.cancelOrder(orderId);
    }


    /**
     * Ushbu metod sotuvchi yetkazib berdim deb belgilagan ammo
     * mijoz qabul qildim deb belgilamagan orderlarni qaytaradi
     */
    @GetMapping("/not-received")
    public ResponseEntity<List<OrderDto>> getNotReceivedOrders(@Valid @RequestBody GetByDates getByDates) {
        return service.getNotReceivedOrders(getByDates);
    }

    @GetMapping("/received")
    public ResponseEntity<List<OrderDto>> getReceivedOrders(@Valid @RequestBody GetByDates getByDates) {
        return service.received(getByDates);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<OrderDto>> rejected(@Valid @RequestBody GetByDates getByDates) {
        return service.rejected(getByDates);
    }
}
