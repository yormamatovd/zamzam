package product.service;

import org.springframework.http.ResponseEntity;
import product.model.ProductDto;
import product.model.ProductRegDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ResponseEntity<ProductDto> create(ProductRegDto dto);

    ResponseEntity<ProductDto> get(Long id);

    ResponseEntity<List<ProductDto>> getSellerProducts(Long sellerId, Integer page);

    ResponseEntity<ProductDto> updatePhoto(UUID photoId,Long productId);

    ResponseEntity<ProductDto> updateDescription(Long productId,String description);

    ResponseEntity<ProductDto> unActivateProduct(Long productId);
    ResponseEntity<String> deleteProduct(Long productId);
}
