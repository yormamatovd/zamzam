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
import order.service.OrderClientService;
import order.session.Session;
import org.bouncycastle.util.Arrays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderClientServiceImpl implements OrderClientService {

    private final MainTemplate mainTemplate;
    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;

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
        order.setPaidAmount(0L);

        orderRepo.save(order);

        return ResponseEntity.ok(mapper.orderToOrderDto(order));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getClientOrders(GetByDates getByDates) {
        if (Session.getUserType() != UserType.CLIENT_USER) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);

        List<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(getByDates.getPage(), 20);

        if (!Arrays.isNullOrEmpty(getByDates.getDates())) {
            for (String date : getByDates.getDates()) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
                Page<Order> ordersPage = orderRepo.findAllByClientIdAndCreatedAtBetween(
                        Session.getInfoId(), Helper.getStartTimeOfDay(dateTime), Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }
        } else {
            Page<Order> ordersPage = orderRepo.findAllByClientId(
                    Session.getInfoId(), pageable);
            orders.addAll(ordersPage.getContent());
        }

        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(Order::getDeliveredDateTime)).collect(Collectors.toList())
        ));
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

    @Override
    public ResponseEntity<List<OrderDto>> getNotReceivedOrders(GetByDates getByDates) {
        if (Session.getUserType() != UserType.CLIENT_USER) throw new NotAcceptableException(ApiStatus.CLIENT_NOT_FOUND);
        List<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(getByDates.getPage(), 20);

        if (!Arrays.isNullOrEmpty(getByDates.getDates())) {
            for (String date : getByDates.getDates()) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
                Page<Order> ordersPage = orderRepo.findClientNotEqualsStatusOrdersByDatesActiveTrue(
                        Session.getInfoId(), OrderStatus.RECEIVED.name(), Helper.getStartTimeOfDay(dateTime), Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }
        } else {
            Page<Order> ordersPage = orderRepo.findClientNotEqualsStatusOrdersActiveTrue(
                    Session.getInfoId(), OrderStatus.RECEIVED.name(), pageable);
            orders.addAll(ordersPage.getContent());
        }

        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(Order::getCreatedAt)).collect(Collectors.toList())
        ));
    }

    @Override
    public ResponseEntity<List<OrderDto>> received(GetByDates getByDates) {
        if (Session.getUserType() != UserType.CLIENT_USER) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);

        List<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(getByDates.getPage(), 20);

        if (!Arrays.isNullOrEmpty(getByDates.getDates())) {
            for (String date : getByDates.getDates()) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
                Page<Order> ordersPage = orderRepo.findAllByStatusAndSellerIdAndRejectedDateTimeBetweenAndActiveTrue(
                        OrderStatus.RECEIVED, Session.getInfoId(), Helper.getStartTimeOfDay(dateTime), Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }
        } else {
            Page<Order> ordersPage = orderRepo.findAllBySellerIdAndStatusAndActiveTrue(
                    Session.getInfoId(), OrderStatus.RECEIVED, pageable);
            orders.addAll(ordersPage.getContent());
        }

        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(Order::getDeliveredDateTime)).collect(Collectors.toList())
        ));
    }

    @Override
    public ResponseEntity<List<OrderDto>> rejected(GetByDates getByDates) {
        if (Session.getUserType() != UserType.CLIENT_USER) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);

        List<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(getByDates.getPage(), 20);

        if (!Arrays.isNullOrEmpty(getByDates.getDates())) {
            for (String date : getByDates.getDates()) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
                Page<Order> ordersPage = orderRepo.findAllByStatusAndClientIdAndActiveTrueAndRejectedDateTimeBetween(
                        OrderStatus.REJECTED, Session.getInfoId(), Helper.getStartTimeOfDay(dateTime), Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }
        } else {
            Page<Order> ordersPage = orderRepo.findAllByClientIdAndStatusAndActiveTrue(
                    Session.getInfoId(), OrderStatus.REJECTED, pageable);
            orders.addAll(ordersPage.getContent());
        }

        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(Order::getDeliveredDateTime)).collect(Collectors.toList())
        ));
    }
}
