package info.service;

import info.model.RegisterUserDto;
import info.model.info.InfoDto;
import org.springframework.http.ResponseEntity;


public interface InfoLocalService {
    ResponseEntity<InfoDto> create(RegisterUserDto dto);

    ResponseEntity<String> getMyPass(String username);

    ResponseEntity<InfoDto> getMyId(String username);

    ResponseEntity<InfoDto> toSeller(Long id);

    ResponseEntity<InfoDto> getInfo(Long infoId);


}
