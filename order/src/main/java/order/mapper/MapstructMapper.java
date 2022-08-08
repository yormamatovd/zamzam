package order.mapper;

import order.entity.Order;
import order.model.ClientDto;
import order.model.OrderDto;
import order.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    @Mapping(target = "product", source = "productDto")
    @Mapping(target = "client", source = "clientDto")
    @Mapping(target = "client", source = "clientDto")
    OrderDto orderToOrderDto(Order order, ProductDto productDto, ClientDto clientDto);

    List<OrderDto> orderToOrderDto(List<Order> orders);
}
