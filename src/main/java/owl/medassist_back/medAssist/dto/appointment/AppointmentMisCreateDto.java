package owl.medassist_back.medAssist.dto.appointment;

import java.time.LocalDateTime;

public record AppointmentMisCreateDto(
        Integer serviceMisId,
        Integer servicePriceMisId,
        Integer specialistMisId,
        LocalDateTime appointmentDateTime,
        String patientFullName,
        String patientPhone
) {
}

