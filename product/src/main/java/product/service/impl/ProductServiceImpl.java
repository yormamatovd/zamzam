package product.service.impl;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import product.entity.Product;
import product.enums.ApiStatus;
import product.enums.ProductType;
import product.enums.UserType;
import product.exceptions.NotAcceptableException;
import product.exceptions.NotFoundException;
import product.exceptions.SystemException;
import product.feign.AttachmentTemplate;
import product.feign.InfoTemplate;
import product.mapper.MapstructMapper;
import product.model.ProductDto;
import product.model.ProductRegDto;
import product.repository.ProductRepo;
import product.service.ProductService;
import product.session.Session;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final InfoTemplate infoTemplate;
    private final AttachmentTemplate attachmentTemplate;
    private final MapstructMapper mapper;

    @Override
    public ResponseEntity<ProductDto> get(Long id) {
        Product byId = productRepo.findById(id).orElseThrow(() -> new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND));
        return ResponseEntity.ok(mapper.productToProductDto(byId));
    }

    @Override
    public ResponseEntity<List<ProductDto>> getSellerProducts(Long sellerId, Integer page) {
        try {
            infoTemplate.getSellerInfo(sellerId);
        } catch (Exception e) {
            throw new NotFoundException(ApiStatus.SELLER_NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, 50, Sort.by("createdAt").descending());

        Page<Product> productPage = productRepo.findAllBySellerIdAndActiveTrue(sellerId, pageable);

        return ResponseEntity.ok(mapper.productToProductDto(productPage.stream().collect(Collectors.toList())));

    }

    @Override
    public ResponseEntity<ProductDto> create(ProductRegDto dto) {
        if (Session.getUserType() != UserType.SELLER_USER) throw new NotAcceptableException(ApiStatus.SELLER_NOT_FOUND);

        try {
            attachmentTemplate.detail(dto.getPhotoId());
        } catch (Exception e) {
            throw new NotFoundException(ApiStatus.PHOTO_NOT_FOUND);
        }

        Product product = new Product();
        product.setCapacity(dto.getCapacity());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPhotoId(dto.getPhotoId());
        product.setType(ProductType.valueOf(dto.getType()));
        product.setWeight(dto.getWeight());
        product.setSellerId(Session.getInfoId());

        productRepo.save(product);

        return ResponseEntity.ok(mapper.productToProductDto(product));
    }

    @Override
    public ResponseEntity<ProductDto> updatePhoto(UUID photoId, Long productId) {
        try {
            attachmentTemplate.detail(photoId);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof FeignException.NotFound) {
                throw new NotFoundException(ApiStatus.PHOTO_NOT_FOUND);
            }
            throw new SystemException(ApiStatus.SERVER_ERROR);
        }
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND));

        if (Session.getUserType() != UserType.SELLER_USER ||
                !Objects.equals(product.getSellerId(), Session.getInfoId()))
            throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        try {
            attachmentTemplate.delete(product.getPhotoId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        product.setPhotoId(photoId);
        return ResponseEntity.ok(mapper.productToProductDto(productRepo.save(product)));
    }

    @Override
    public ResponseEntity<ProductDto> updateDescription(Long productId, String description) {
        if (description.length()<1000)throw new NotAcceptableException(ApiStatus.DESCRIPTION_LARGE);

        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND));

        if (!Objects.equals(product.getSellerId(), Session.getInfoId()) ||
            Session.getUserType()!=UserType.SELLER_USER)throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        product.setDescription(description);

        return ResponseEntity.ok(mapper.productToProductDto(productRepo.save(product)));
    }


    @Override
    public ResponseEntity<ProductDto> unActivateProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND));

        if (!Objects.equals(product.getSellerId(), Session.getInfoId()) ||
                Session.getUserType()!=UserType.SELLER_USER)throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        product.setActive(false);

        return ResponseEntity.ok(mapper.productToProductDto(product));

    }

    @Override
    public ResponseEntity<String> deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new NotFoundException(ApiStatus.PRODUCT_NOT_FOUND));

        if (!Objects.equals(product.getSellerId(), Session.getInfoId()) ||
                Session.getUserType()!=UserType.SELLER_USER)throw new NotAcceptableException(ApiStatus.NOT_ACCESS);

        productRepo.delete(product);

        return ResponseEntity.ok("OK");

    }
}
