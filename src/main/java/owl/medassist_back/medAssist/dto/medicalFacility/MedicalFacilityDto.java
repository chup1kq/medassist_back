package owl.medassist_back.medAssist.dto.medicalFacility;

public record MedicalFacilityDto(
        Integer id,
        String name,
        String address,
        String description
) {
}

