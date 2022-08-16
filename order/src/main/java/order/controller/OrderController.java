package order.controller;

import lombok.RequiredArgsConstructor;
import order.model.OrderDto;
import order.model.OrderRegDto;
import order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    /**
     *  This method for Global network
     * @param dto for create new order
     * @return OrderDto
     */
    @PostMapping
    public ResponseEntity<OrderDto> makeOrder(@Valid @RequestBody OrderRegDto dto) {
        return service.makeOrder(dto);
    }



    /**
     * This method for Global network
     * This method for get client orders
     * @param page is which page
     * @param sinceDateTime is start date time
     * @param untilDateTime is end date time
     * @return List of OrderDto
     */
    @GetMapping("/client-orders")
    public ResponseEntity<List<OrderDto>> getClientOrders(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "since", required = false) Long sinceDateTime,
                                                          @RequestParam(name = "until", required = false) Long untilDateTime) {
        return service.getClientOrders(page, sinceDateTime, untilDateTime);
    }






    /**
     * This method for Global network
     * @param orderId for canceling
     * @return OrderDto
     */
    @PutMapping("/cancel")
    public ResponseEntity<OrderDto> cancelOrder(@RequestParam(name = "orderId") Long orderId) {
        return service.cancelOrder(orderId);
    }

}
