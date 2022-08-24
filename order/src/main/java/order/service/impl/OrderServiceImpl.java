package order.service.impl;

import lombok.RequiredArgsConstructor;
import order.entity.Order;
import order.enums.OrderStatus;
import order.helper.Helper;
import order.mapper.MapstructMapper;
import order.model.GetByDates;
import order.model.OrderDto;
import order.repository.OrderRepo;
import order.service.OrderService;
import order.session.Session;
import org.bouncycastle.util.Arrays;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;


    private List<Order> getByStatusAndDate(List<OrderStatus> statuses, LocalDate date, Pageable pageable) {

        if (date != null) {
            return orderRepo.findAllByClientIdOrSellerIdAndStatusInAndUpdatedAtBetween(
                    Session.getInfoId(), Session.getInfoId(), statuses,
                    Helper.getStartTimeOfDay(date), Helper.getEndTimeOfDay(date), pageable).getContent();
        } else {
            return orderRepo.findAllBySellerIdOrClientIdAndStatusIn(
                    Session.getInfoId(), Session.getInfoId(), statuses, pageable).getContent();
        }
    }

    @Override
    public ResponseEntity<List<OrderDto>> byStatus(List<OrderStatus> statuses, GetByDates getByDates) {
        List<Order> orders = new ArrayList<>();
        Pageable pageable = PageRequest.of(getByDates.getPage(), 20);
        if (!Arrays.isNullOrEmpty(getByDates.getDates())) {
            for (String dateString : getByDates.getDates())
                orders.addAll(getByStatusAndDate(statuses, Helper.parseDate(dateString, "yyyy-MM-dd"), pageable));
            return ResponseEntity.ok(mapper.orderToOrderDto(orders));
        } else return ResponseEntity.ok(mapper.orderToOrderDto(getByStatusAndDate(statuses, null, pageable)));
    }
}
