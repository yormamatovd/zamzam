package order.service.impl;

import lombok.RequiredArgsConstructor;
import order.model.OrderDto;
import order.model.OrderRegDto;
import order.service.OrderLocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLocalServiceImpl implements OrderLocalService {
    @Override
    public ResponseEntity<List<OrderDto>> getSellerOrders(Long sellerId, Integer page, Long sinceDateTime, Long untilDateTime) {
        return null;
    }

    @Override
    public ResponseEntity<List<OrderDto>> getClientOrders(Long clientId, Integer page, Long sinceDateTime, Long untilDateTime) {
        return null;
    }

    @Override
    public ResponseEntity<OrderDto> acceptOrder(Long orderId, Long sellerId) {
        return null;
    }

    @Override
    public ResponseEntity<OrderDto> setEstimated(Long dateTimeSeconds) {
        return null;
    }

    @Override
    public ResponseEntity<OrderDto> makeOrder(OrderRegDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<OrderDto> cancelOrder(Long orderId, Long clientId) {
        return null;
    }
}
