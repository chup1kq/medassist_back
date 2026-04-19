package owl.medassist_back.medAssist.dto.schedule;

import java.time.LocalTime;

public record ScheduleDto(
        Integer scheduleId,
        Integer specialistFacilityId,
        Integer dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
}

