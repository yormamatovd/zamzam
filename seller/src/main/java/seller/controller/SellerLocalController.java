package seller.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import seller.model.SellerDto;
import seller.service.SellerLocalService;

@RestController
@RequestMapping("local/seller")
@RequiredArgsConstructor
public class SellerLocalController {

    private final SellerLocalService service;


    @PostMapping("/create")
    public ResponseEntity<SellerDto> createSeller(@RequestParam(name = "infoId") Long infoId){
        return service.create(infoId);
    }
}
