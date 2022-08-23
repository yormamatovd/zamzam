package order.mapper;

import lombok.RequiredArgsConstructor;
import order.entity.Order;
import order.feign.MainTemplate;
import order.helper.Helper;
import order.model.OrderDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapstructMapper {

    private final MainTemplate mainTemplate;

    public OrderDto orderToOrderDto(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OrderDto dto = new OrderDto();
        dto.setClientId(order.getClientId());
        dto.setProductId(order.getProductId());
        dto.setCount(order.getCount());
        dto.setBottles(order.getCount() - order.getReturnedBottlesAmount());
        dto.setReceivedTime(
                order.getClientReceivedDateTime() != 0 ?
                        Helper.timeAsSeconds(order.getClientReceivedDateTime()).format(formatter) : "");
        dto.setDeliveredTime(
                order.getDeliveredDateTime() != 0 ?
                        Helper.timeAsSeconds(order.getDeliveredDateTime()).format(formatter) : "");
        dto.setOrderedTime(
                order.getOrderedDateTime()!=0?
                Helper.timeAsSeconds(order.getOrderedDateTime()).format(formatter):"");
        dto.setEstimateDeliveryTime(
                order.getEstimatedDeliveryDateTime()!=0?
                Helper.timeAsSeconds(order.getEstimatedDeliveryDateTime()).format(formatter):"");
        dto.setStatus(order.getStatus().name());
        dto.setReason(order.getReason() != null ? order.getReason().name() : null);
        dto.setSum((mainTemplate.getProduct(order.getProductId()).getPrice() * order.getCount()) + "");
        dto.setPaid(order.getPaidAmount() + "");
        return dto;
    }


    public List<OrderDto> orderToOrderDto(List<Order> orders) {
        return orders.stream().map(this::orderToOrderDto).collect(Collectors.toList());
    }
}
