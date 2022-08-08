package order.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import order.entity.Order;
import order.enums.ApiStatus;
import order.enums.OrderStatus;
import order.enums.UserType;
import order.exception.NotAcceptableException;
import order.exception.NotFoundException;
import order.exception.SystemException;
import order.feign.InfoTemplate;
import order.feign.ProductTemplate;
import order.mapper.MapstructMapper;
import order.model.ClientDto;
import order.model.OrderDto;
import order.model.OrderRegDto;
import order.model.ProductDto;
import order.repository.OrderRepo;
import order.service.OrderService;
import order.session.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ProductTemplate productTemplate;
    private final InfoTemplate infoTemplate;
    private final OrderRepo orderRepo;
    private final MapstructMapper mapper;

    @Override
    public ResponseEntity<OrderDto> makeOrder(OrderRegDto dto) {
        if (Session.getUserType() != UserType.CLIENT_USER) throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        ProductDto productDto;
        ClientDto clientDto;

        try {
            productDto = productTemplate.get(dto.getProductId()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        if (productDto == null) throw new SystemException(ApiStatus.SERVER_ERROR);

        try {
            clientDto = infoTemplate.getClientInfo(Session.getInfoId()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        if (clientDto == null) throw new SystemException(ApiStatus.SERVER_ERROR);


        Order order = new Order();
        order.setClientId(clientDto.getInfo().getId());
        order.setCount(dto.getCount());
        order.setProductId(productDto.getId());
        order.setStatus(OrderStatus.WAIT_SELLER_ACCEPT);
        order.setPaidAmount(0D);

        orderRepo.save(order);

        return ResponseEntity.ok(mapper.orderToOrderDto(order, productDto, clientDto));
    }
}
