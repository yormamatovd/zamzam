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

    /**
     * This method for Local network
     * This method for get seller orders
     * @param sellerId for identify which seller
     * @param page is which page
     * @param sinceDateTime is start date time
     * @param untilDateTime is end date time
     * @return List of OrderDto
     */
    @GetMapping("/get-seller-orders")
    public ResponseEntity<List<OrderDto>> getSellerOrders(@RequestParam(name = "sellerId") Long sellerId,
                                                          @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "sinceWhen", required = false) Long sinceDateTime,
                                                          @RequestParam(name = "untilWhen", required = false) Long untilDateTime) {

        return service.getSellerOrders(sellerId, page, sinceDateTime, untilDateTime);
    }

    /**
     * This method for Local network
     * This method for get client orders
     * @param clientId for identify which client
     * @param page is which page
     * @param sinceDateTime is start date time
     * @param untilDateTime is end date time
     * @return List of OrderDto
     */
    @GetMapping("/get-client-orders")
    public ResponseEntity<List<OrderDto>> getClientOrders(@RequestParam(name = "clientId") Long clientId,
                                                          @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(name = "sinceWhen", required = false) Long sinceDateTime,
                                                          @RequestParam(name = "untilWhen", required = false) Long untilDateTime) {

        return service.getClientOrders(clientId, page, sinceDateTime, untilDateTime);
    }


    /**
     * This method for Local network
     * This method for sellers
     * @param orderId for which order is accepted
     * @param sellerId for who accept this order
     * @return OrderDto
     */
    @PutMapping("/set-seller-accept")
    public ResponseEntity<OrderDto> acceptOrder(@RequestParam(name = "orderId") Long orderId,
                                                @RequestParam(name = "sellerId") Long sellerId) {
        return service.acceptOrder(orderId,sellerId);
    }

    /**
     * This method for Local network
     * @param dateTimeSeconds for set when product is delivered
     * @return OrderDto
     */
    @PutMapping("/set-estimate-delivery")
    public ResponseEntity<OrderDto> setEstimatedDateTime(@RequestParam(name = "dateTimeSeconds") Long dateTimeSeconds) {
        return service.setEstimated(dateTimeSeconds);
    }

    /**
     * This method for Local network
     * @param orderId for canceling
     * @param clientId for which client is canceling order
     * @return OrderDto
     */
    @PutMapping("/cancel")
    public ResponseEntity<OrderDto> cancelOrder(@RequestParam(name = "orderId") Long orderId,
                                                @RequestParam(name = "clientId") Long clientId) {
        return service.cancelOrder(orderId, clientId);
    }



}
