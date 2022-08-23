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

    @PutMapping("/accept")
    public ResponseEntity<OrderDto> acceptOrder(@RequestParam(name = "orderId") Long orderId) {
        return service.acceptOrder(orderId);
    }

    @PutMapping("/estimate-delivery")
    public ResponseEntity<OrderDto> setEstimatedDateTime(@RequestParam(name = "time") Long dateTimeSeconds,
                                                         @RequestParam(name = "orderId") Long orderId) {
        return service.setEstimated(dateTimeSeconds,orderId);
    }

    @PutMapping("/reject")
    public ResponseEntity<String> rejectOrder(@RequestParam(name = "reason") String reason,
                                                         @RequestParam(name = "orderId") Long orderId) {
        return service.reject(reason,orderId);
    }


    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getSellerOrders(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "dates", required = false,defaultValue = "[]") String[] dates,
                                                          @RequestParam(name = "by",required = false,defaultValue = "ALL") String by) {
        return service.getSellerOrders(page, dates,by);
    }

    @GetMapping("/today-work")
    public ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        return service.getSellerOrdersDeliveryToday(page);
    }

    @GetMapping("/delivered")
    public ResponseEntity<List<OrderDto>> delivered(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(name = "dates", required = false,defaultValue = "[]") String[] dates){
        return service.delivered(page,dates);
    }
    @GetMapping("/rejected")
    public ResponseEntity<List<OrderDto>> rejected(@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(name = "dates", required = false,defaultValue = "[]") String[] dates){
        return service.rejected(page,dates);
    }

}
