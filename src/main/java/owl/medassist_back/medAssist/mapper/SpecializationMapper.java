package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.specialist.SpecializationDto;
import owl.medassist_back.medAssist.entity.specialist.Specialization;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {

    SpecializationDto toDto(Specialization specialization);

    Specialization toEntity(SpecializationDto specializationDto);
}
