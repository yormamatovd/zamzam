package seller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import seller.model.InfoDto;

import java.util.List;

@FeignClient("INFO")
public interface InfoTemplate {
    @GetMapping("local/info")
    ResponseEntity<InfoDto> getInfo(@RequestParam(name = "infoId") Long infoId);
    @PutMapping("local/info/to-seller")
    ResponseEntity<InfoDto> toSeller(@RequestParam(name = "infoId") Long id);

    @GetMapping("local/info/seller-info")
    ResponseEntity<InfoDto> getSellerInfo(@RequestParam(name = "infoId") Long id);
    @GetMapping("local/info/sellers-info")
    ResponseEntity<List<InfoDto>> getSellersInfo(@RequestParam(name = "page") Integer page);
}
