package info.service;

import info.model.token.CheckCodeDto;
import org.springframework.http.ResponseEntity;

public interface UpdateVerifyService {
    ResponseEntity<String> updatePasswordVerify(CheckCodeDto dto);

}
