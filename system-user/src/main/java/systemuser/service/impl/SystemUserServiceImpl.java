package systemuser.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import systemuser.enums.ApiStatus;
import systemuser.exception.NotFoundException;
import systemuser.exception.SystemException;
import systemuser.feign.SellerTemplate;
import systemuser.model.ClientDto;
import systemuser.model.SellerDto;
import systemuser.service.SystemUserService;

@Service
@RequiredArgsConstructor
public class SystemUserServiceImpl implements SystemUserService {


}
