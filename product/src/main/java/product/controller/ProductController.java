package product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.model.ProductDto;
import product.model.ProductRegDto;
import product.service.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<ProductDto> get(@RequestParam(name = "productId") Long id) {
        return service.get(id);
    }

    @GetMapping("/seller-products")
    public ResponseEntity<List<ProductDto>> getSellerProducts(@RequestParam Long sellerId, @RequestParam(required = false) Integer page) {
        return service.getSellerProducts(sellerId, page);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductRegDto dto) {
        return service.create(dto);
    }

    @PutMapping("/update-description")
    ResponseEntity<ProductDto> updateDescription(@RequestParam(name = "productId") Long productId, @RequestParam(name = "description") String description) {
        return service.updateDescription(productId, description);
    }

    @PutMapping("/update-photo")
    ResponseEntity<ProductDto> updatePhoto(@RequestParam(name = "productId") Long productId, @RequestParam(name = "photoId") UUID photoId) {
        return service.updatePhoto(photoId, productId);
    }

    @PutMapping("/un-activate")
    ResponseEntity<ProductDto> unActivateProduct(@RequestParam(name = "productId") Long productId) {
        return service.unActivateProduct(productId);
    }

    @DeleteMapping()
    ResponseEntity<String> delete(@RequestParam(name = "productId") Long productId) {
        return service.deleteProduct(productId);
    }

}
