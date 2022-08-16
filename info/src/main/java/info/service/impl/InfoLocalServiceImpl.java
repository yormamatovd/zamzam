package info.service.impl;

import info.entity.Info;
import info.enums.ApiStatus;
import info.enums.UserType;
import info.exception.NotFoundException;
import info.mapper.MapstructMapper;
import info.model.RegisterUserDto;
import info.model.info.InfoDto;
import info.repository.InfoRepo;
import info.service.InfoLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InfoLocalServiceImpl implements InfoLocalService {
    private final InfoRepo infoRepo;
    private final MapstructMapper mapper;

    @Value("${info.default.photo-id}")
    private String defaultPhotoId;

    @Override
    public ResponseEntity<InfoDto> getInfo(Long infoId) {
        return ResponseEntity.ok(mapper.infoToInfoDto(infoRepo.findById(infoId).orElseThrow(() -> new NotFoundException(ApiStatus.INFO_NOT_FOUND))));
    }

    @Override
    public ResponseEntity<InfoDto> create(RegisterUserDto dto) {

        Info info = new Info();
        info.setName(dto.getName());
        info.setSurname(dto.getSurname());
        info.setPhone(dto.getPhone());
        info.setEmail(dto.getEmail());
        info.setPassword(dto.getPassword());
        info.setType(dto.getType());
        info.setVerified(true);
        info.setPhotoId(UUID.fromString(defaultPhotoId));
        return ResponseEntity.ok(mapper.infoToInfoDto(infoRepo.save(info)));
    }

    @Override
    public ResponseEntity<InfoDto> getMyId(String username) {
        Optional<Info> infoOptional = infoRepo.findByEmailOrPhoneAndActiveTrue(username, username);
        return infoOptional.map(info -> ResponseEntity.ok(mapper.infoToInfoDto(info))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<String> getMyPass(String username) {
        Optional<Info> infoOptional = infoRepo.findByEmailOrPhoneAndActiveTrue(username, username);
        return infoOptional.map(info -> ResponseEntity.ok(info.getPassword())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<InfoDto> toSeller(Long id) {
        Optional<Info> byId = infoRepo.findById(id);
        if (byId.isEmpty()) throw new NotFoundException(ApiStatus.INFO_NOT_FOUND);
        byId.get().setType(UserType.SELLER_USER);
        return ResponseEntity.ok(mapper.infoToInfoDto(infoRepo.save(byId.get())));
    }
}
