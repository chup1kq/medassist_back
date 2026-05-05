package owl.medassist_back.medAssist.dto.appointment;

import java.time.LocalDate;
import java.util.List;

public record SchedulePeriodDto(
        LocalDate startDate,
        LocalDate endDate,
        List<ScheduleDayDto> days
) {
}

