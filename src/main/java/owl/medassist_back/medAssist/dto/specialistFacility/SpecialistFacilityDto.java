package owl.medassist_back.medAssist.dto.specialistFacility;

public record SpecialistFacilityDto(
        Integer id,
        Integer specialistId,
        String specialistName,
        Integer facilityId,
        String facilityName
) {
}

