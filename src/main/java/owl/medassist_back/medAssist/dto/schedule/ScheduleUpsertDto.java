package owl.medassist_back.medAssist.dto.schedule;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record ScheduleUpsertDto(
        Integer specialistId,
        Integer facilityId,
        @NotNull(message = "dayOfWeek is required")
        @Min(value = 1, message = "dayOfWeek must be in range 1..7")
        @Max(value = 7, message = "dayOfWeek must be in range 1..7")
        Integer dayOfWeek,
        @NotNull(message = "startTime is required")
        LocalTime startTime,
        @NotNull(message = "endTime is required")
        LocalTime endTime
) {

    public ScheduleUpsertDto(Integer specialistFacilityId, Integer specialistId, Integer facilityId,
                             Integer dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this(specialistId, facilityId, dayOfWeek, startTime, endTime);
    }
}

