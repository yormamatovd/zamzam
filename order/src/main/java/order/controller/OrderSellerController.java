package order.controller;

import lombok.RequiredArgsConstructor;
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

    /**
     * This method for Global network
     * This method for sellers
     * @param orderId for which order is accepted
     * @return OrderDto
     */
    @PutMapping("/accept")
    public ResponseEntity<OrderDto> acceptOrder(@RequestParam(name = "orderId") Long orderId) {
        return service.acceptOrder(orderId);
    }
    /**
     * This method for Global network
     * @param dateTimeSeconds for set when product is delivered
     * @return OrderDto
     */
    @PutMapping("/estimate-delivery")
    public ResponseEntity<OrderDto> setEstimatedDateTime(@RequestParam(name = "time") Long dateTimeSeconds,
                                                         @RequestParam(name = "orderId") Long orderId) {
        return service.setEstimated(dateTimeSeconds,orderId);
    }

    /**
     * This method for Global network
     * This method for get seller orders
     * @param page is which page
     * @param sinceDateTime is start date time
     * @param untilDateTime is end date time
     * @return List of OrderDto
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getSellerOrders(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "since", required = false) Long sinceDateTime,
                                                          @RequestParam(name = "until", required = false) Long untilDateTime) {
        return service.getSellerOrders(page, sinceDateTime, untilDateTime);
    }
    /**
     * This method for Global network
     * This method is used to get the seller's orders for delivery today
     * @param page is which page
     * @return List of OrderDto
     */
    @GetMapping("/today-work")
    public ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return service.getSellerOrdersDeliveryToday(page);
    }

}
