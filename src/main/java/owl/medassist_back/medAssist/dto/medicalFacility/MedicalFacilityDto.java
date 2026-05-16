package owl.medassist_back.medAssist.dto.medicalFacility;

import java.util.List;

public record MedicalFacilityDto(
        Integer id,
        String name,
        String address,
        String description,
        List<MedicalFacilityScheduleDto> schedules
) {
}

