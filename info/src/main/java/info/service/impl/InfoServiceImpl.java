package info.service.impl;

import info.annotation.Gmail;
import info.annotation.Phone;
import info.entity.Info;
import info.enums.ApiStatus;
import info.enums.UserType;
import info.exception.NotFoundException;
import info.mapper.MapstructMapper;
import info.model.ClientDto;
import info.model.SellerDto;
import info.repository.InfoRepo;
import info.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepo infoRepo;
    private final MapstructMapper mapper;

    @Override
    public ResponseEntity<Boolean> existByEmail(String email) {
        if (!Gmail.GmailValidator.isValid(email)) return ResponseEntity.ok(true);
        return ResponseEntity.ok(infoRepo.existsByEmail(email));
    }

    @Override
    public ResponseEntity<Boolean> existByPhone(String phone) {
        if (!Phone.PhoneValidator.isValid(phone)) return ResponseEntity.ok(true);
        return ResponseEntity.ok(infoRepo.existsByPhone(phone));
    }

    @Override
    public ResponseEntity<ClientDto> getClientInfo(Long id) {
        Info info = infoRepo.findById(id).orElseThrow(() -> new NotFoundException(ApiStatus.CLIENT_NOT_FOUND));
        if (info.getType() != UserType.CLIENT_USER) throw new NotFoundException(ApiStatus.CLIENT_NOT_FOUND);
        return ResponseEntity.ok(new ClientDto(mapper.infoToInfoDto(info)));
    }

    @Override
    public ResponseEntity<SellerDto> getSellerInfo(Long id) {
        Info info = infoRepo.findById(id).orElseThrow(() -> new NotFoundException(ApiStatus.SELLER_NOT_FOUND));
        if (info.getType() != UserType.SELLER_USER) throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        return ResponseEntity.ok(new SellerDto(mapper.infoToInfoDto(info)));
    }

    @Override
    public ResponseEntity<List<SellerDto>> getSellersInfo(Integer page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("name").descending());
        Page<Info> sellersPage = infoRepo.findAllByTypeAndActiveTrue(UserType.SELLER_USER, pageable);
        return ResponseEntity.ok(
                mapper.infoToInfoDto(sellersPage.stream().collect(Collectors.toList()))
                        .stream().map(SellerDto::new).collect(Collectors.toList()));
    }
}
