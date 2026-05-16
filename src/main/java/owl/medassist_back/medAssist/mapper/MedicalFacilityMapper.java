package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityDto;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;

@Mapper(
        componentModel = "spring",
        uses = MedicalFacilityScheduleMapper.class
)
public interface MedicalFacilityMapper {

    @Mapping(source = "schedules", target = "schedules")
    MedicalFacilityDto toDto(MedicalFacility medicalFacility);
}

