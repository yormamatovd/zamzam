package info.controller;

import info.model.ClientDto;
import info.model.SellerDto;
import info.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
@Validated
public class InfoController {

    private final InfoService service;

    @PostMapping("/exist-email")
    public ResponseEntity<Boolean> existEmail(@RequestParam(name = "email") String email) {
        return service.existByEmail(email);
    }
    @PostMapping("/exist-phone")
    public ResponseEntity<Boolean> existPhone(@RequestParam(name = "phone") String phone) {
        return service.existByPhone(phone);
    }

    @GetMapping("/client")
    ResponseEntity<ClientDto> getClientInfo(@RequestParam(name = "clientId") Long id) {
        return service.getClientInfo(id);
    }
    @GetMapping("/seller")
    ResponseEntity<SellerDto> getSellerInfo(@RequestParam(name = "sellerId") Long id) {
        return service.getSellerInfo(id);
    }

    @GetMapping("/sellers")
    ResponseEntity<List<SellerDto>> getSellersInfo(
            @RequestParam(name = "page", required = false, defaultValue = "0")
            @Min(value = 0, message = "WRONG_PAGE") Integer page) {
        return service.getSellersInfo(page);
    }

}
