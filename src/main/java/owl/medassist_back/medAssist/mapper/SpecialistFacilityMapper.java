package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityDto;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;

@Mapper(componentModel = "spring")
public interface SpecialistFacilityMapper {

    @Mapping(source = "specialist.id", target = "specialistId")
    @Mapping(source = "specialist.fullName", target = "specialistName")
    @Mapping(source = "facility.id", target = "facilityId")
    @Mapping(source = "facility.name", target = "facilityName")
    SpecialistFacilityDto toDto(SpecialistFacility specialistFacility);
}

