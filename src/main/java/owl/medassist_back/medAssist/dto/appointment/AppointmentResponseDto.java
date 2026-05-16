package owl.medassist_back.medAssist.dto.appointment;

import java.time.LocalDateTime;

public record AppointmentResponseDto(
        Integer appointmentId,
        Integer serviceId,
        Integer specialistId,
        LocalDateTime appointmentDateTime,
        String patientFullName,
        String patientPhone,
        String status,
        LocalDateTime createdAt
) {
}

