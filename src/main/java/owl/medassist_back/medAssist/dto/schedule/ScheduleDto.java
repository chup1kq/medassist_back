package owl.medassist_back.medAssist.dto.schedule;

import java.time.LocalTime;

public record ScheduleDto(
        Integer id,
        Integer dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
}

