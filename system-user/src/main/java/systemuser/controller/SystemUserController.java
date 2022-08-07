package systemuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import systemuser.model.SellerDto;
import systemuser.service.SystemUserService;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService service;

    @PostMapping("/make-seller")
    public ResponseEntity<SellerDto> makeSeller(@RequestParam(name = "infoId") Long id) {
        return service.makeSeller(id);
    }

}
