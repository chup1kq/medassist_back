package owl.medassist_back.medAssist.dto.appointment;

import java.time.LocalDate;
import java.util.List;

public record ScheduleDayDto(
        LocalDate date,
        List<ScheduleSlotDto> slots
) {
}

