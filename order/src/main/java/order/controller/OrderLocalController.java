package order.controller;

import lombok.RequiredArgsConstructor;
import order.model.OrderDto;
import order.model.OrderRegDto;
import order.service.OrderLocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/local/order")
@RequiredArgsConstructor
public class OrderLocalController {

    private final OrderLocalService service;

    @GetMapping("/get-seller-orders")
    public ResponseEntity<List<OrderDto>> getSellerOrders(@RequestParam(name = "sellerId") Long sellerId,
                                                          @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "sinceWhen", required = false) Long sinceDateTime,
                                                          @RequestParam(name = "untilWhen", required = false) Long untilDateTime) {

        return service.getSellerOrders(sellerId, page, sinceDateTime, untilDateTime);
    }

    @GetMapping("/get-client-orders")
    public ResponseEntity<List<OrderDto>> getClientOrders(@RequestParam(name = "clientId") Long clientId,
                                                          @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "sinceWhen", required = false) Long sinceDateTime,
                                                          @RequestParam(name = "untilWhen", required = false) Long untilDateTime) {

        return service.getClientOrders(clientId, page, sinceDateTime, untilDateTime);
    }

    @PutMapping("/set-seller-accept")
    public ResponseEntity<OrderDto> acceptOrder(@RequestParam(name = "orderId") Long orderId,
                                                @RequestParam(name = "sellerId") Long sellerId) {
        return service.acceptOrder(orderId,sellerId);
    }

    @PutMapping("/set-estimate-delivery")
    public ResponseEntity<OrderDto> setEstimatedDateTime(@RequestParam(name = "dateTimeSeconds") Long dateTimeSeconds) {
        return service.setEstimated(dateTimeSeconds);
    }

    @PutMapping("/cancel")
    public ResponseEntity<OrderDto> cancelOrder(@RequestParam(name = "orderId") Long orderId,
                                                @RequestParam(name = "clientId") Long clientId) {
        return service.cancelOrder(orderId, clientId);
    }

    @PostMapping
    public ResponseEntity<OrderDto> makeOrder(@Valid @RequestBody OrderRegDto dto) {
        return service.makeOrder(dto);
    }
}
