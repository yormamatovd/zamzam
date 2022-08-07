package info.controller;

import info.model.ClientDto;
import info.model.RegisterUserDto;
import info.model.info.InfoDto;
import info.service.InfoLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("local/info")
@RequiredArgsConstructor
public class InfoLocalController {

    private final InfoLocalService service;

    @GetMapping
    public ResponseEntity<InfoDto> getInfo(@RequestParam(name = "infoId") Long infoId) {
        return service.getInfo(infoId);
    }

    @GetMapping("/my-pass")
    public ResponseEntity<String> getMyPassword(@RequestParam(name = "username") String username) {
        return service.getMyPass(username);
    }

    @GetMapping("/my-id")
    public ResponseEntity<InfoDto> getMyId(@RequestParam(name = "username") String username) {
        return service.getMyId(username);
    }

    @PostMapping("/create")
    public ResponseEntity<InfoDto> createInfo(@RequestBody RegisterUserDto dto) {
        return service.create(dto);
    }

    @GetMapping("/client-info")
    ResponseEntity<ClientDto> getClientInfo(@RequestParam(name = "infoId") Long id) {
        return service.getClientInfo(id);
    }
    @GetMapping("/seller-info")
    ResponseEntity<InfoDto> getSellerInfo(@RequestParam(name = "infoId") Long id) {
        return service.getSellerInfo(id);
    }
    @GetMapping("/sellers-infos")
    ResponseEntity<List<InfoDto>> getSellersInfo(@RequestParam(name = "page",required = false) Integer page) {
        return service.getSellersInfo(page);
    }



    @PutMapping("/to-seller")
    ResponseEntity<InfoDto> toSeller(@RequestParam(name = "infoId") Long id) {
        return service.toSeller(id);
    }
}
