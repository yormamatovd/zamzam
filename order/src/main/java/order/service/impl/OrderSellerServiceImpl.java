package order.service.impl;

import lombok.RequiredArgsConstructor;
import order.entity.Order;
import order.entity.abs.AbsMain;
import order.enums.ApiStatus;
import order.enums.OrderStatus;
import order.enums.RejectReasons;
import order.enums.UserType;
import order.exception.BadRequestException;
import order.exception.NotAcceptableException;
import order.exception.NotFoundException;
import order.helper.Helper;
import order.mapper.MapstructMapper;
import order.model.OrderDto;
import order.repository.OrderRepo;
import order.service.OrderSellerService;
import order.session.Session;
import org.bouncycastle.util.Arrays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderSellerServiceImpl implements OrderSellerService {

    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;

    @Override
    public ResponseEntity<List<OrderDto>> getSellerOrders(Integer page, String[] dates, String by) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        Pageable pageable = PageRequest.of(page, 20);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Order> orders = new ArrayList<>();
        if (!Arrays.isNullOrEmpty(dates)) {
            for (String date : dates) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);

                Page<Order> ordersPage = orderRepo.findAllBySellerIdAndCreatedAtBetween(
                        Session.getInfoId(), Helper.getStartTimeOfDay(dateTime),
                        Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }

        } else {
            Page<Order> ordersPage = orderRepo.findAllBySellerId(Session.getInfoId(), pageable);
            orders.addAll(ordersPage.getContent());
        }


        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(AbsMain::getCreatedAt)).collect(Collectors.toList())));
    }

    @Override
    public ResponseEntity<List<OrderDto>> getSellerOrdersDeliveryToday(Integer page) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        ZonedDateTime zonedDateTime = Helper.currentZonedDateTime();
        LocalDate localDate = zonedDateTime.toLocalDate();

        Pageable pageable = PageRequest.of(page, 10, Sort.by("estimatedDeliveryDateTime").ascending());

        Page<Order> ordersPage = orderRepo.findAllBySellerIdAndEstimatedDeliveryDateTimeBetweenAndActiveTrue(
                Session.getInfoId(),
                Helper.getStartTimeOfDay(localDate),
                Helper.getEndTimeOfDay(localDate),
                pageable
        );
        return ResponseEntity.ok(mapper.orderToOrderDto(ordersPage.toList()));
    }


    @Override
    public ResponseEntity<OrderDto> acceptOrder(Long orderId) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        Order order = orderRepo.findByIdAndActiveTrue(orderId).orElseThrow(() -> new NotFoundException(ApiStatus.ORDER_NOT_FOUND));
        if (!order.getSellerId().equals(Session.getInfoId()))
            throw new BadRequestException(ApiStatus.NOT_ACCESS);

        order.setStatus(OrderStatus.WAIT_DELIVERY);
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
        if (order.getStatus()==OrderStatus.WAIT_SELLER_ACCEPT) {
            order.setSellerAcceptedDateTime(Helper.currentSeconds());
            order.setStatus(OrderStatus.WAIT_DELIVERY);
        }
        orderRepo.save(order);

        return ResponseEntity.ok(mapper.orderToOrderDto(order));
    }

    @Override
    public ResponseEntity<String> reject(String reason, Long orderId) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);

        if (!RejectReasons.exist(reason)) throw new NotAcceptableException(ApiStatus.REASON_NOT_ACCEPTABLE);

        Order order = orderRepo.findByIdAndActiveTrue(orderId).orElseThrow(() -> new NotFoundException(ApiStatus.ORDER_NOT_FOUND));

        if (!order.getSellerId().equals(Session.getInfoId())) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);

        order.setActive(false);
        order.setEstimatedDeliveryDateTime(0L);
        order.setStatus(OrderStatus.REJECTED);
        order.setReason(RejectReasons.valueOf(reason));
        orderRepo.save(order);

        return ResponseEntity.ok("OK");
    }

    @Override
    public ResponseEntity<List<OrderDto>> delivered(Integer page, String[] dates) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);

        List<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(page, 20);

        if (!Arrays.isNullOrEmpty(dates)) {
            for (String date : dates) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
                Page<Order> ordersPage = orderRepo.findAllByStatusAndSellerIdAndDeliveredDateTimeBetweenAndActiveTrue(
                        OrderStatus.DELIVERED, Session.getInfoId(), Helper.getStartTimeOfDay(dateTime), Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }
        } else {
            Page<Order> ordersPage = orderRepo.findAllBySellerIdAndStatusAndActiveTrue(Session.getInfoId(), OrderStatus.DELIVERED, pageable);
            orders.addAll(ordersPage.getContent());
        }

        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(Order::getDeliveredDateTime)).collect(Collectors.toList())
        ));
    }

    @Override
    public ResponseEntity<List<OrderDto>> rejected(Integer page, String[] dates) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);

        List<Order> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(page, 20);

        if (!Arrays.isNullOrEmpty(dates)) {
            for (String date : dates) {
                LocalDate dateTime = null;
                try {
                    dateTime = LocalDate.parse(date, formatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dateTime == null) throw new NotAcceptableException(ApiStatus.NOT_ACCEPTABLE);
                Page<Order> ordersPage = orderRepo.findAllByStatusAndSellerIdAndRejectedDateTimeBetweenAndActiveTrue(
                        OrderStatus.REJECTED, Session.getInfoId(), Helper.getStartTimeOfDay(dateTime), Helper.getEndTimeOfDay(dateTime), pageable);
                orders.addAll(ordersPage.getContent());
            }
        } else {
            Page<Order> ordersPage = orderRepo.findAllBySellerIdAndStatusAndActiveTrue(
                    Session.getInfoId(), OrderStatus.REJECTED, pageable);
            orders.addAll(ordersPage.getContent());
        }

        return ResponseEntity.ok(mapper.orderToOrderDto(
                orders.stream().sorted(Comparator.comparing(Order::getDeliveredDateTime)).collect(Collectors.toList())
        ));
    }


}
