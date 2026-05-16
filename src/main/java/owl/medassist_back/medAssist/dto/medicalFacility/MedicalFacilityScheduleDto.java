package owl.medassist_back.medAssist.dto.medicalFacility;

import java.time.LocalTime;

public record MedicalFacilityScheduleDto(
        Integer id,
        Integer facilityId,
        String facilityName,
        Integer dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        Boolean isClosed,
        Boolean is24Hours
) {
}


