package owl.medassist_back.medAssist.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import owl.medassist_back.medAssist.dto.appointment.AppointmentMisCreateDto;
import owl.medassist_back.medAssist.dto.appointment.AppointmentResponseDto;
import owl.medassist_back.medAssist.dto.appointment.ScheduleSlotDto;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class MedicalInformationSystemClient {

    private final RestTemplate restTemplate;

    @Value("${spring.mis.api.base-url}")
    private String misApiBaseUrl;

    public AppointmentResponseDto createAppointment(AppointmentMisCreateDto appointmentRequest) {
        String url = misApiBaseUrl + "/appointments";

        HttpHeaders headers = createHeaders();
        HttpEntity<AppointmentMisCreateDto> entity = new HttpEntity<>(appointmentRequest, headers);

        ResponseEntity<AppointmentResponseDto> response = restTemplate.postForEntity(
                url,
                entity,
                AppointmentResponseDto.class
        );

        if (response.getBody() == null) {
            throw new IllegalStateException("MIS returned empty body for appointment creation");
        }

        log.info("Appointment created successfully in MIS: {}", response.getBody());
        return response.getBody();
    }

//    public void cancelAppointment(Integer appointmentId, String cancellationReason) {
//        String url = misApiBaseUrl + "/appointments/" + appointmentId + "/cancel";
//
//        HttpHeaders headers = createHeaders();
//        Map<String, String> requestBody = Map.of(
//                "appointmentId", appointmentId.toString(),
//                "cancellationReason", cancellationReason != null ? cancellationReason : ""
//        );
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);
//
//        restTemplate.postForEntity(
//                url,
//                entity,
//                Void.class
//        );
//
//        log.info("Appointment {} cancelled successfully in MIS", appointmentId);
//    }

    public List<ScheduleSlotDto> getScheduleForDay(Integer specialistId, LocalDate date) {
        String url = misApiBaseUrl + "/specialists/" + specialistId + "/schedule/day?date=" + date;

        HttpHeaders headers = createHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ScheduleSlotDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ScheduleSlotDto[].class
        );

        ScheduleSlotDto[] body = response.getBody();
        if (body == null) {
            return List.of();
        }

        log.debug("Retrieved {} schedule slots for specialist {} on {}", body.length, specialistId, date);
        return List.of(body);
    }

    public List<ScheduleSlotDto> getScheduleForTwoWeeks(Integer specialistId, LocalDate startDate) {
        String url = misApiBaseUrl + "/specialists/" + specialistId + "/schedule/two-weeks?startDate=" + startDate;

        HttpHeaders headers = createHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ScheduleSlotDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ScheduleSlotDto[].class
        );

        ScheduleSlotDto[] body = response.getBody();
        if (body == null) {
            return List.of();
        }

        log.debug("Retrieved {} schedule slots for specialist {} for two weeks starting {}", body.length, specialistId, startDate);
        return List.of(body);
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}


