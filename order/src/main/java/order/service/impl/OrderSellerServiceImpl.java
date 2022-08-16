package order.service.impl;

import lombok.RequiredArgsConstructor;
import order.entity.Order;
import order.enums.ApiStatus;
import order.enums.UserType;
import order.exception.BadRequestException;
import order.exception.NotFoundException;
import order.feign.MainTemplate;
import order.helper.Helper;
import order.mapper.MapstructMapper;
import order.model.OrderDto;
import order.model.SellerDto;
import order.repository.OrderRepo;
import order.service.OrderSellerService;
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
public class OrderSellerServiceImpl implements OrderSellerService {

    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;
    private final MainTemplate mainTemplate;

    @Override
    public ResponseEntity<List<OrderDto>> getSellerOrders(Integer page, Long sinceDateTime, Long untilDateTime) {
        SellerDto seller = mainTemplate.getSeller(Session.getInfoId());
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").ascending());
        Page<Order> ordersPage = orderRepo.getSellerActiveOrders(sinceDateTime, untilDateTime,
                seller.getInfo().getId(),
                pageable);
        return ResponseEntity.ok(mapper.orderToOrderDto(ordersPage.toList()));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(Integer page) {
        ZonedDateTime zonedDateTime = Helper.currentZonedDateTime();
        LocalDate localDate = zonedDateTime.toLocalDate();
        LocalDateTime startDayTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        LocalDateTime endDayTime = LocalDateTime.of(localDate, LocalTime.of(23, 59, 59));

        return getSellerOrders(page,
                startDayTime.toEpochSecond(zonedDateTime.getOffset()),
                endDayTime.toEpochSecond(zonedDateTime.getOffset()));
    }


    @Override
    public ResponseEntity<OrderDto> acceptOrder(Long orderId) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        Order order = orderRepo.findByIdAndActiveTrue(orderId).orElseThrow(() -> new NotFoundException(ApiStatus.ORDER_NOT_FOUND));
        if (!order.getSellerId().equals(Session.getInfoId()))
            throw new BadRequestException(ApiStatus.NOT_ACCESS);

        order.setAcceptedBySeller(true);
        order.setSellerAcceptedDateTime(Helper.currentSeconds());
        orderRepo.save(order);
        return ResponseEntity.ok(mapper.orderToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> setEstimated(Long dateTimeSeconds, Long orderId) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        Order order = orderRepo.findByIdAndActiveTrue(orderId).orElseThrow(() -> new NotFoundException(ApiStatus.ORDER_NOT_FOUND));
        if (!order.getSellerId().equals(Session.getInfoId()))
            throw new BadRequestException(ApiStatus.NOT_ACCESS);

        order.setEstimatedDeliveryDateTime(dateTimeSeconds);
        if (!order.isAcceptedBySeller()) {
            order.setSellerAcceptedDateTime(Helper.currentSeconds());
            order.setAcceptedBySeller(true);
        }
        orderRepo.save(order);

        return ResponseEntity.ok(mapper.orderToOrderDto(order));
    }
}
