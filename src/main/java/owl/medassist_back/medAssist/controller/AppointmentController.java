package owl.medassist_back.medAssist.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.medAssist.dto.appointment.AppointmentCreateDto;
import owl.medassist_back.medAssist.dto.appointment.AppointmentResponseDto;
import owl.medassist_back.medAssist.dto.appointment.ScheduleDayDto;
import owl.medassist_back.medAssist.dto.appointment.SchedulePeriodDto;
import owl.medassist_back.medAssist.service.AppointmentService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/med-assist/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDto createAppointment(@Valid @RequestBody AppointmentCreateDto request) {
        return appointmentService.createAppointment(request);
    }

//    @DeleteMapping("/{appointmentId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void cancelAppointment(
//            @PathVariable @Min(1) Integer appointmentId,
//            @RequestParam(required = false) String reason
//    ) {
//        appointmentService.cancelAppointment(appointmentId, reason);
//    }

    @GetMapping("/specialists/{specialistId}/schedule/day")
    public ScheduleDayDto getScheduleForDay(
            @PathVariable @Min(1) Integer specialistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        if (date == null) {
            date = LocalDate.now();
        }
        return appointmentService.getScheduleForDay(specialistId, date);
    }

    @GetMapping("/specialists/{specialistId}/schedule/two-weeks")
    public SchedulePeriodDto getScheduleForTwoWeeks(
            @PathVariable @Min(1) Integer specialistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
    ) {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
        return appointmentService.getScheduleForTwoWeeks(specialistId, startDate);
    }
}

