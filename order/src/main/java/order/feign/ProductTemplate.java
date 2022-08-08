package order.feign;

import order.model.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PRODUCT")
public interface ProductTemplate {
    @GetMapping("/product")
    ResponseEntity<ProductDto> get(@RequestParam(name = "productId") Long id);
}
