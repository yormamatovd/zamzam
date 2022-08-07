package info.mapper;

import info.entity.Info;
import info.model.info.InfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {

    @Mapping(target = "type", expression = "java(info.getType().name())")
    @Mapping(target = "photoId", expression = "java(info.getPhotoId().toString())")
    InfoDto infoToInfoDto(Info info);
    List<InfoDto> infoToInfoDto(List<Info> infos);
}
