package systemuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import systemuser.service.SystemUserService;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemUserController {

    private final SystemUserService service;


}
