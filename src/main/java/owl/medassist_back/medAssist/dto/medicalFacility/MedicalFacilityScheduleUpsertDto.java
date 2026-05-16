package owl.medassist_back.medAssist.dto.medicalFacility;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record MedicalFacilityScheduleUpsertDto(
        @NotNull(message = "dayOfWeek is required")
        @Min(value = 1, message = "dayOfWeek must be in range 1..7")
        @Max(value = 7, message = "dayOfWeek must be in range 1..7")
        Integer dayOfWeek,
        LocalTime startTime,
        LocalTime endTime,
        @NotNull(message = "isClosed is required")
        Boolean isClosed,
        @NotNull(message = "is24Hours is required")
        Boolean is24Hours
) {
}


