package owl.medassist_back.medAssist.dto.specialistFacility;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SpecialistFacilityUpsertDto(
        @NotNull(message = "specialistId is required")
        @Positive(message = "specialistId must be greater than 0")
        Integer specialistId,

        @NotNull(message = "facilityId is required")
        @Positive(message = "facilityId must be greater than 0")
        Integer facilityId
) {
}

