package product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import product.entity.Product;
import product.model.ProductDto;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    @Mapping(target = "type", expression = "java(product.getType().name())")
    @Mapping(target = "photoId", expression = "java(product.getPhotoId().toString())")
    ProductDto productToProductDto(Product product);

    List<ProductDto> productToProductDto(List<Product> products);
}
