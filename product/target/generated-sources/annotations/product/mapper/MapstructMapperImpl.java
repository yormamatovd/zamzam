package product.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import product.entity.Product;
import product.model.ProductDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-11T02:38:10+0500",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class MapstructMapperImpl implements MapstructMapper {

    @Override
    public ProductDto productToProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setActive( product.isActive() );
        productDto.setDescription( product.getDescription() );
        productDto.setId( product.getId() );
        productDto.setName( product.getName() );
        productDto.setPrice( product.getPrice() );
        productDto.setSellerId( product.getSellerId() );

        productDto.setType( product.getType().name() );
        productDto.setPhotoId( product.getPhotoId().toString() );
        productDto.setCapacity( product.getCapacity()/1000 );
        productDto.setWeight( product.getWeight()/1000 );

        return productDto;
    }

    @Override
    public List<ProductDto> productToProductDto(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( productToProductDto( product ) );
        }

        return list;
    }
}
