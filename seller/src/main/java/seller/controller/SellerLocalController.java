package seller.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seller.model.RegisterUserDto;
import seller.model.SellerDto;
import seller.service.SellerLocalService;

import javax.validation.Valid;

@RestController
@RequestMapping("local/seller")
@RequiredArgsConstructor
public class SellerLocalController {

    private final SellerLocalService service;


    @PostMapping("/create")
    public ResponseEntity<SellerDto> createSeller(@Valid @RequestBody RegisterUserDto registerUserDto) {
        return service.create(registerUserDto);
    }
}
