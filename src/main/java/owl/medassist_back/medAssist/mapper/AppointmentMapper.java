package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.appointment.AppointmentCreateDto;
import owl.medassist_back.medAssist.dto.appointment.AppointmentMisCreateDto;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;
import owl.medassist_back.medAssist.entity.specialist.Specialist;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    default AppointmentMisCreateDto toMisCreateDto(
            AppointmentCreateDto appointmentRequest,
            Specialist specialist,
            MedicalService service,
            ServicePrice servicePrice) {
        
        return new AppointmentMisCreateDto(
                service.getMisId(),
                servicePrice.getMisId(),
                specialist.getMisId(),
                appointmentRequest.appointmentDateTime(),
                appointmentRequest.patientFullName(),
                appointmentRequest.patientPhone()
        );
    }
}

