package order.controller;

import lombok.RequiredArgsConstructor;
import order.model.OrderDto;
import order.model.OrderRegDto;
import order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    /**
     *  This method for global network
     * @param dto for create new order
     * @return OrderDto
     */
    @PostMapping
    public ResponseEntity<OrderDto> makeOrder(@Valid @RequestBody OrderRegDto dto) {
        return service.makeOrder(dto);
    }
}
