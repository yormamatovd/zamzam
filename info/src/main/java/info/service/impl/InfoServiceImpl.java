package info.service.impl;

import info.enums.ApiStatus;
import info.exception.NotFoundException;
import info.mapper.MapstructMapper;
import info.model.info.InfoDto;
import info.repository.InfoRepo;
import info.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepo infoRepo;
    private final MapstructMapper mapper;

    @Override
    public ResponseEntity<Boolean> existByEmail(String email) {
        return ResponseEntity.ok(infoRepo.existsByEmail(email));
    }

}
