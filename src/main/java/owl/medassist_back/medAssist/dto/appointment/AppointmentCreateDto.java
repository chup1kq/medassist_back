package owl.medassist_back.medAssist.dto.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

public record AppointmentCreateDto(
        @NotNull(message = "serviceId is required")
        @Min(1)
        Integer serviceId,

        @NotNull(message = "servicePriceId is required")
        @Min(1)
        Integer servicePriceId,
        
        @NotNull(message = "specialistId is required")
        @Min(1)
        Integer specialistId,
        
        @NotNull(message = "appointmentDateTime is required")
        LocalDateTime appointmentDateTime,
        
        @NotNull(message = "patientFullName is required")
        String patientFullName,
        
        String patientPhone
) {
}

