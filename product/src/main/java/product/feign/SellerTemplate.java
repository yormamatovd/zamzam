package product.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("SELLER")
public interface SellerTemplate {


}
