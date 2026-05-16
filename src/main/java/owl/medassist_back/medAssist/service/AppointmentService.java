package owl.medassist_back.medAssist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import owl.medassist_back.exception.BaseAppException;
import owl.medassist_back.exception.ExceptionName;
import owl.medassist_back.medAssist.dto.appointment.AppointmentCreateDto;
import owl.medassist_back.medAssist.dto.appointment.AppointmentResponseDto;
import owl.medassist_back.medAssist.dto.appointment.ScheduleDayDto;
import owl.medassist_back.medAssist.dto.appointment.SchedulePeriodDto;
import owl.medassist_back.medAssist.dto.appointment.ScheduleSlotDto;
import owl.medassist_back.medAssist.integration.MedicalInformationSystemClient;
import owl.medassist_back.medAssist.mapper.AppointmentMapper;
import owl.medassist_back.medAssist.repository.SpecialistRepository;
import owl.medassist_back.medAssist.repository.MedicalServiceRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AppointmentService {

    private final MedicalInformationSystemClient misClient;
    private final AppointmentMapper appointmentMapper;
    private final SpecialistRepository specialistRepository;
    private final MedicalServiceRepository medicalServiceRepository;

    public AppointmentResponseDto createAppointment(AppointmentCreateDto appointmentRequest) {
        var specialist = specialistRepository.findById(appointmentRequest.specialistId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));

        var service = medicalServiceRepository.findById(appointmentRequest.serviceId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        var servicePrice = service.getPrices().stream()
                .filter(price -> price.getId().equals(appointmentRequest.servicePriceId()))
                .findFirst()
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        log.info("Creating appointment in MIS for specialist {} and service {}", 
                appointmentRequest.specialistId(), appointmentRequest.serviceId());

        var appointmentMisRequest = appointmentMapper.toMisCreateDto(
                appointmentRequest,
                specialist,
                service,
                servicePrice
        );

        AppointmentResponseDto response;
        try {
            response = misClient.createAppointment(appointmentMisRequest);
        } catch (ResourceAccessException exception) {
            log.error("MIS is unavailable while creating appointment", exception);
            throw new BaseAppException(ExceptionName.MIS_UNAVAILABLE);
        } catch (RestClientResponseException exception) {
            log.error("MIS returned error while creating appointment: status={}, body={}",
                    exception.getRawStatusCode(), exception.getResponseBodyAsString(), exception);
            throw new BaseAppException(ExceptionName.APPOINTMENT_CREATE_FAILED);
        } catch (Exception exception) {
            log.error("Unexpected error while creating appointment in MIS", exception);
            throw new BaseAppException(ExceptionName.APPOINTMENT_CREATE_FAILED);
        }

        log.info("Appointment created successfully with ID: {}", response.appointmentId());

        return response;
    }

//    public void cancelAppointment(Integer appointmentId, String cancellationReason) {
//        if (appointmentId == null || appointmentId <= 0) {
//            throw new BaseAppException(ExceptionName.INVALID_APPOINTMENT_ID);
//        }
//
//        log.info("Cancelling appointment with ID: {} (reason: {})", appointmentId, cancellationReason);
//
//        try {
//            misClient.cancelAppointment(appointmentId, cancellationReason);
//        } catch (ResourceAccessException exception) {
//            log.error("MIS is unavailable while cancelling appointment {}", appointmentId, exception);
//            throw new BaseAppException(ExceptionName.MIS_UNAVAILABLE);
//        } catch (RestClientResponseException exception) {
//            log.error("MIS returned error while cancelling appointment {}: status={}, body={}",
//                    appointmentId, exception.getRawStatusCode(), exception.getResponseBodyAsString(), exception);
//            throw new BaseAppException(ExceptionName.APPOINTMENT_CANCEL_FAILED);
//        } catch (Exception exception) {
//            log.error("Unexpected error while cancelling appointment {}", appointmentId, exception);
//            throw new BaseAppException(ExceptionName.APPOINTMENT_CANCEL_FAILED);
//        }
//
//        log.info("Appointment {} cancelled successfully", appointmentId);
//    }

    @Transactional(readOnly = true)
    public ScheduleDayDto getScheduleForDay(Integer specialistId, LocalDate date) {
        var specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));

        if (date.isBefore(LocalDate.now())) {
            throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_DATE);
        }

        log.debug("Fetching schedule for specialist {} on date {}", specialistId, date);

        List<ScheduleSlotDto> slots;
        try {
            slots = misClient.getScheduleForDay(specialist.getMisId(), date);
        } catch (ResourceAccessException exception) {
            log.error("MIS is unavailable while fetching daily schedule for specialist {}", specialistId, exception);
            throw new BaseAppException(ExceptionName.MIS_UNAVAILABLE);
        } catch (RestClientResponseException exception) {
            log.error("MIS returned error while fetching daily schedule for specialist {}: status={}, body={}",
                    specialistId, exception.getRawStatusCode(), exception.getResponseBodyAsString(), exception);
            throw new BaseAppException(ExceptionName.SCHEDULE_DAY_FETCH_FAILED);
        } catch (Exception exception) {
            log.error("Unexpected error while fetching daily schedule for specialist {}", specialistId, exception);
            throw new BaseAppException(ExceptionName.SCHEDULE_DAY_FETCH_FAILED);
        }

        return new ScheduleDayDto(date, slots);
    }

    @Transactional(readOnly = true)
    public SchedulePeriodDto getScheduleForTwoWeeks(Integer specialistId, LocalDate startDate) {
        var specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));

        if (startDate.isBefore(LocalDate.now())) {
            throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_DATE);
        }

        log.debug("Fetching two-week schedule for specialist {} starting from {}", specialistId, startDate);

        List<ScheduleSlotDto> slots;
        try {
            slots = misClient.getScheduleForTwoWeeks(specialist.getMisId(), startDate);
        } catch (ResourceAccessException exception) {
            log.error("MIS is unavailable while fetching two-week schedule for specialist {}", specialistId, exception);
            throw new BaseAppException(ExceptionName.MIS_UNAVAILABLE);
        } catch (RestClientResponseException exception) {
            log.error("MIS returned error while fetching two-week schedule for specialist {}: status={}, body={}",
                    specialistId, exception.getRawStatusCode(), exception.getResponseBodyAsString(), exception);
            throw new BaseAppException(ExceptionName.SCHEDULE_PERIOD_FETCH_FAILED);
        } catch (Exception exception) {
            log.error("Unexpected error while fetching two-week schedule for specialist {}", specialistId, exception);
            throw new BaseAppException(ExceptionName.SCHEDULE_PERIOD_FETCH_FAILED);
        }

        Map<LocalDate, List<ScheduleSlotDto>> groupedByDate = new LinkedHashMap<>();
        for (int i = 0; i < 14; i++) {
            groupedByDate.put(startDate.plusDays(i), new ArrayList<>());
        }

        for (ScheduleSlotDto slot : slots) {
            LocalDate slotDate = slot.startTime().toLocalDate();
            groupedByDate.computeIfAbsent(slotDate, ignored -> new ArrayList<>()).add(slot);
        }

        List<ScheduleDayDto> days = groupedByDate.entrySet().stream()
                .map(entry -> new ScheduleDayDto(entry.getKey(), entry.getValue()))
                .toList();

        return new SchedulePeriodDto(startDate, startDate.plusDays(13), days);
    }
}

