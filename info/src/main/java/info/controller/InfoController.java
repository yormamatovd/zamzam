package info.controller;

import info.model.ClientDto;
import info.model.RegisterUserDto;
import info.model.SellerDto;
import info.model.info.InfoDto;
import info.model.info.UpdateEmailDto;
import info.model.info.UpdateNameSurnameDto;
import info.model.info.UpdatePasswordDto;
import info.model.token.TokenDto;
import info.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {

    private final InfoService service;

    @PostMapping("/exist-email")
    public ResponseEntity<Boolean> existEmail(@RequestParam(name = "email") String email) {
        return service.existByEmail(email);
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
    ResponseEntity<List<SellerDto>> getSellersInfo(@RequestParam(name = "page",required = false,defaultValue = "0") Integer page) {
        return service.getSellersInfo(page);
    }

}
