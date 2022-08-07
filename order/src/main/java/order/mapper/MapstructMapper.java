package order.mapper;

import order.entity.Order;
import order.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    @Mapping(target = "type", expression = "java(info.getType().name())")
    @Mapping(target = "photoId", expression = "java(info.getPhotoId().toString())")
    OrderDto orderToOrderDto(Order order);

    List<OrderDto> orderToOrderDto(List<Order> orders);
}
