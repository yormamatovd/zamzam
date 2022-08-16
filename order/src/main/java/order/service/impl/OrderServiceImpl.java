package order.service.impl;

import lombok.RequiredArgsConstructor;
import order.entity.Order;
import order.enums.ApiStatus;
import order.enums.OrderStatus;
import order.enums.UserType;
import order.exception.BadRequestException;
import order.exception.NotAcceptableException;
import order.exception.NotFoundException;
import order.feign.MainTemplate;
import order.helper.Helper;
import order.mapper.MapstructMapper;
import order.model.*;
import order.repository.OrderRepo;
import order.service.OrderService;
import order.session.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;
    private final MainTemplate mainTemplate;


    @Override
    public ResponseEntity<OrderDto> makeOrder(OrderRegDto dto) {
        if (Session.getUserType() != UserType.CLIENT_USER) throw new NotAcceptableException(ApiStatus.CLIENT_NOT_FOUND);

        ProductDto productDto = mainTemplate.getProduct(dto.getProductId());
        ClientDto clientDto = mainTemplate.getClient(Session.getInfoId());

        Order order = new Order();
        order.setClientId(clientDto.getInfo().getId());
        order.setSellerId(Session.getInfoId());
        order.setCount(dto.getCount());
        order.setProductId(productDto.getId());
        order.setStatus(OrderStatus.WAIT_SELLER_ACCEPT);
        order.setPaidAmount(0D);

        orderRepo.save(order);

        return ResponseEntity.ok(mapper.orderToOrderDto(order));
    }




    @Override
    public ResponseEntity<List<OrderDto>> getClientOrders(Integer page, Long sinceDateTime, Long untilDateTime) {
        ClientDto client = mainTemplate.getClient(Session.getInfoId());
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        Page<Order> ordersPage = orderRepo.getClientActiveOrders(sinceDateTime, untilDateTime,
                client.getInfo().getId(), pageable);
        return ResponseEntity.ok(mapper.orderToOrderDto(ordersPage.toList()));
    }




    @Override
    public ResponseEntity<OrderDto> cancelOrder(Long orderId) {
        ClientDto client = mainTemplate.getClient(Session.getInfoId());

        Order order = orderRepo.findByIdAndActiveTrue(orderId).orElseThrow(() -> new NotFoundException(ApiStatus.ORDER_NOT_FOUND));
        if (!order.getClientId().equals(client.getInfo().getId())) throw new BadRequestException(ApiStatus.NOT_ACCESS);

        order.setActive(false);
        orderRepo.save(order);

        return ResponseEntity.ok(mapper.orderToOrderDto(order));
    }
}
