package order.service.impl;

import lombok.RequiredArgsConstructor;
import order.feign.InfoTemplate;
import order.feign.ProductTemplate;
import order.mapper.MapstructMapper;
import order.model.OrderDto;
import order.repository.OrderRepo;
import order.service.OrderLocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLocalServiceImpl implements OrderLocalService {

    private final ProductTemplate productTemplate;
    private final InfoTemplate infoTemplate;
    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;

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
    public ResponseEntity<OrderDto> cancelOrder(Long orderId, Long clientId) {
        return null;
    }
}
