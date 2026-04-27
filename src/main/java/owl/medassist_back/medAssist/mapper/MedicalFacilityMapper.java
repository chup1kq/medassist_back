package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityDto;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;

@Mapper(componentModel = "spring")
public interface MedicalFacilityMapper {

    MedicalFacilityDto toDto(MedicalFacility medicalFacility);
}

