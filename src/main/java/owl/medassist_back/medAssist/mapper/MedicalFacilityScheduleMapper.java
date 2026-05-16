package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityScheduleDto;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacilitySchedule;

@Mapper(componentModel = "spring")
public interface MedicalFacilityScheduleMapper {

    @Mapping(source = "medicalFacility.id", target = "facilityId")
    @Mapping(source = "medicalFacility.name", target = "facilityName")
    MedicalFacilityScheduleDto toDto(MedicalFacilitySchedule schedule);
}

