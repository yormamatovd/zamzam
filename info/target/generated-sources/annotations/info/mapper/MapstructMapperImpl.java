package info.mapper;

import info.entity.Info;
import info.model.info.InfoDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-11T02:38:06+0500",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class MapstructMapperImpl implements MapstructMapper {

    @Override
    public InfoDto infoToInfoDto(Info info) {
        if ( info == null ) {
            return null;
        }

        InfoDto infoDto = new InfoDto();

        infoDto.setId( info.getId() );
        infoDto.setName( info.getName() );
        infoDto.setSurname( info.getSurname() );

        infoDto.setType( info.getType().name() );
        infoDto.setPhotoId( info.getPhotoId().toString() );

        return infoDto;
    }

    @Override
    public List<InfoDto> infoToInfoDto(List<Info> infos) {
        if ( infos == null ) {
            return null;
        }

        List<InfoDto> list = new ArrayList<InfoDto>( infos.size() );
        for ( Info info : infos ) {
            list.add( infoToInfoDto( info ) );
        }

        return list;
    }
}
