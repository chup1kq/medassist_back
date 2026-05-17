package owl.medassist_back.medAssist.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.medAssist.dto.appointment.*;
import owl.medassist_back.medAssist.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/med-assist/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDto createAppointment(@Valid @RequestBody AppointmentCreateDto request) {
//        return appointmentService.createAppointment(request);
        return appointmentStub(request);
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
//        return appointmentService.getScheduleForDay(specialistId, date);

        return getScheduleForDayStub(specialistId, date);
    }

    @GetMapping("/specialists/{specialistId}/schedule/two-weeks")
    public SchedulePeriodDto getScheduleForTwoWeeks(
            @PathVariable @Min(1) Integer specialistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
    ) {
        if (startDate == null) {
            startDate = LocalDate.now();
        }
//        return appointmentService.getScheduleForTwoWeeks(specialistId, startDate);

        return getScheduleForTwoWeeksStub(specialistId, startDate);
    }

    private AppointmentResponseDto appointmentStub(@Valid @RequestBody AppointmentCreateDto request) {
        return new AppointmentResponseDto(
                1001,
                request.serviceId(),
                request.specialistId(),
                request.appointmentDateTime(),
                request.patientFullName(),
                request.patientPhone(),
                "CONFIRMED",
                LocalDateTime.now()
        );
    }

    private ScheduleDayDto getScheduleForDayStub(
            @PathVariable @Min(1) Integer specialistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ScheduleSlotDto> slots = List.of(
                new ScheduleSlotDto(
                        1,
                        specialistId,
                        "Иванов Иван Иванович",
                        1,
                        date.atTime(9, 0),
                        date.atTime(9, 30),
                        true
                ),
                new ScheduleSlotDto(
                        2,
                        specialistId,
                        "Иванов Иван Иванович",
                        1,
                        date.atTime(9, 30),
                        date.atTime(10, 0),
                        false
                ),
                new ScheduleSlotDto(
                        3,
                        specialistId,
                        "Иванов Иван Иванович",
                        2,
                        date.atTime(10, 0),
                        date.atTime(10, 30),
                        true
                ),
                new ScheduleSlotDto(
                        4,
                        specialistId,
                        "Иванов Иван Иванович",
                        2,
                        date.atTime(10, 30),
                        date.atTime(11, 0),
                        true
                ),
                new ScheduleSlotDto(
                        5,
                        specialistId,
                        "Иванов Иван Иванович",
                        3,
                        date.atTime(11, 0),
                        date.atTime(11, 30),
                        false
                ),
                new ScheduleSlotDto(
                        6,
                        specialistId,
                        "Иванов Иван Иванович",
                        3,
                        date.atTime(11, 30),
                        date.atTime(12, 0),
                        true
                ),
                new ScheduleSlotDto(
                        7,
                        specialistId,
                        "Иванов Иван Иванович",
                        4,
                        date.atTime(13, 0),
                        date.atTime(13, 30),
                        true
                ),
                new ScheduleSlotDto(
                        8,
                        specialistId,
                        "Иванов Иван Иванович",
                        4,
                        date.atTime(13, 30),
                        date.atTime(14, 0),
                        true
                ),
                new ScheduleSlotDto(
                        9,
                        specialistId,
                        "Иванов Иван Иванович",
                        5,
                        date.atTime(14, 0),
                        date.atTime(14, 30),
                        false
                ),
                new ScheduleSlotDto(
                        10,
                        specialistId,
                        "Иванов Иван Иванович",
                        5,
                        date.atTime(14, 30),
                        date.atTime(15, 0),
                        true
                )
        );

        return new ScheduleDayDto(date, slots);
    }

    private SchedulePeriodDto getScheduleForTwoWeeksStub(
            @PathVariable @Min(1) Integer specialistId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
    ) {
        List<ScheduleDayDto> days = new ArrayList<>();

        for (int i = 0; i < 14; i++) {

            LocalDate currentDate = startDate.plusDays(i);

            List<ScheduleSlotDto> slots = List.of(
                    new ScheduleSlotDto(
                            i * 10 + 1,
                            specialistId,
                            "Иванов Иван Иванович",
                            1,
                            currentDate.atTime(9, 0),
                            currentDate.atTime(9, 30),
                            true
                    ),
                    new ScheduleSlotDto(
                            i * 10 + 2,
                            specialistId,
                            "Иванов Иван Иванович",
                            1,
                            currentDate.atTime(9, 30),
                            currentDate.atTime(10, 0),
                            false
                    ),
                    new ScheduleSlotDto(
                            i * 10 + 3,
                            specialistId,
                            "Иванов Иван Иванович",
                            2,
                            currentDate.atTime(10, 0),
                            currentDate.atTime(10, 30),
                            true
                    ),
                    new ScheduleSlotDto(
                            i * 10 + 4,
                            specialistId,
                            "Иванов Иван Иванович",
                            2,
                            currentDate.atTime(10, 30),
                            currentDate.atTime(11, 0),
                            true
                    ),
                    new ScheduleSlotDto(
                            i * 10 + 5,
                            specialistId,
                            "Иванов Иван Иванович",
                            3,
                            currentDate.atTime(11, 0),
                            currentDate.atTime(11, 30),
                            false
                    ),
                    new ScheduleSlotDto(
                            i * 10 + 6,
                            specialistId,
                            "Иванов Иван Иванович",
                            3,
                            currentDate.atTime(11, 30),
                            currentDate.atTime(12, 0),
                            true
                    )
            );

            days.add(new ScheduleDayDto(currentDate, slots));
        }

        return new SchedulePeriodDto(
                startDate,
                startDate.plusDays(13),
                days
        );
    }

}

