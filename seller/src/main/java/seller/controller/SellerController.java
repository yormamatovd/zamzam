package seller.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import seller.model.SellerDto;
import seller.service.SellerService;

import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService service;

    @GetMapping
    public ResponseEntity<SellerDto> getSeller(@RequestParam(name = "sellerId") Long id) {
        return service.getSeller(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SellerDto>> getSellers(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return service.getSellers(page);
    }
}
