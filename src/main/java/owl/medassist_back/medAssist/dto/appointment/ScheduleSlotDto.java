package owl.medassist_back.medAssist.dto.appointment;

import java.time.LocalDateTime;

public record ScheduleSlotDto(
        Integer slotId,
        Integer specialistId,
        String specialistName,
        Integer serviceId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Boolean isAvailable
) {
}

