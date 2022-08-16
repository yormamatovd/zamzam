package order.feign;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import order.enums.ApiStatus;
import order.exception.NotFoundException;
import order.exception.SystemException;
import order.model.ClientDto;
import order.model.ProductDto;
import order.model.SellerDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainTemplate {

    private final ProductTemplate productTemplate;
    private final InfoTemplate infoTemplate;


    public ProductDto getProduct(Long productId) {
        ProductDto productDto;
        try {
            productDto = productTemplate.get(productId).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        if (productDto == null) throw new SystemException(ApiStatus.SERVER_ERROR);
        return productDto;
    }

    public ClientDto getClient(Long clientId) {
        ClientDto clientDto;
        try {
            clientDto = infoTemplate.getClientInfo(clientId).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        if (clientDto == null) throw new SystemException(ApiStatus.SERVER_ERROR);
        return clientDto;
    }

    public SellerDto getSeller(Long sellerId) {
        SellerDto sellerDto;
        try {
            sellerDto = infoTemplate.getSellerInfo(sellerId).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        if (sellerDto == null) throw new SystemException(ApiStatus.SERVER_ERROR);
        return sellerDto;
    }
}
