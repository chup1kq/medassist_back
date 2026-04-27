package owl.medassist_back.medAssist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.medAssist.dto.document.DocumentDto;
import owl.medassist_back.medAssist.dto.document.DocumentUpsertDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityUpsertDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceCardDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceUpsertDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleUpsertDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceUpsertDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistCardDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistUpsertDto;
import owl.medassist_back.medAssist.service.MedAssistAdminService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MedAssistAdminController {

    private final MedAssistAdminService medAssistAdminService;

    @PostMapping("/services")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalServiceCardDto createService(@Valid @RequestBody MedicalServiceUpsertDto request) {
        return medAssistAdminService.createService(request);
    }

    @PutMapping("/services/{serviceId}")
    public MedicalServiceCardDto updateService(@PathVariable Integer serviceId, @Valid @RequestBody MedicalServiceUpsertDto request) {
        return medAssistAdminService.updateService(serviceId, request);
    }

    @DeleteMapping("/services/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Integer serviceId) {
        medAssistAdminService.deleteService(serviceId);
    }

    @PostMapping("/service-prices")
    @ResponseStatus(HttpStatus.CREATED)
    public ServicePriceDto createServicePrice(@Valid @RequestBody ServicePriceUpsertDto request) {
        return medAssistAdminService.createServicePrice(request);
    }

    @PutMapping("/service-prices/{priceId}")
    public ServicePriceDto updateServicePrice(@PathVariable Integer priceId, @Valid @RequestBody ServicePriceUpsertDto request) {
        return medAssistAdminService.updateServicePrice(priceId, request);
    }

    @DeleteMapping("/service-prices/{priceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServicePrice(@PathVariable Integer priceId) {
        medAssistAdminService.deleteServicePrice(priceId);
    }

    @PostMapping("/specialists")
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialistCardDto createSpecialist(@Valid @RequestBody SpecialistUpsertDto request) {
        return medAssistAdminService.createSpecialist(request);
    }

    @PutMapping("/specialists/{specialistId}")
    public SpecialistCardDto updateSpecialist(@PathVariable Integer specialistId, @Valid @RequestBody SpecialistUpsertDto request) {
        return medAssistAdminService.updateSpecialist(specialistId, request);
    }

    @DeleteMapping("/specialists/{specialistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialist(@PathVariable Integer specialistId) {
        medAssistAdminService.deleteSpecialist(specialistId);
    }

    @PostMapping("/documents")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentDto createDocument(@Valid @RequestBody DocumentUpsertDto request) {
        return medAssistAdminService.createDocument(request);
    }

    @PutMapping("/documents/{documentId}")
    public DocumentDto updateDocument(@PathVariable Integer documentId, @Valid @RequestBody DocumentUpsertDto request) {
        return medAssistAdminService.updateDocument(documentId, request);
    }

    @DeleteMapping("/documents/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@PathVariable Integer documentId) {
        medAssistAdminService.deleteDocument(documentId);
    }

    @PostMapping("/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDto createSchedule(@Valid @RequestBody ScheduleUpsertDto request) {
        return medAssistAdminService.createSchedule(request);
    }

    @PutMapping("/schedules/{scheduleId}")
    public ScheduleDto updateSchedule(@PathVariable Integer scheduleId, @Valid @RequestBody ScheduleUpsertDto request) {
        return medAssistAdminService.updateSchedule(scheduleId, request);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Integer scheduleId) {
        medAssistAdminService.deleteSchedule(scheduleId);
    }

    @PostMapping("/facilities")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalFacilityDto createFacility(@Valid @RequestBody MedicalFacilityUpsertDto request) {
        return medAssistAdminService.createFacility(request);
    }

    @PutMapping("/facilities/{facilityId}")
    public MedicalFacilityDto updateFacility(@PathVariable Integer facilityId, @Valid @RequestBody MedicalFacilityUpsertDto request) {
        return medAssistAdminService.updateFacility(facilityId, request);
    }

    @DeleteMapping("/facilities/{facilityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacility(@PathVariable Integer facilityId) {
        medAssistAdminService.deleteFacility(facilityId);
    }
}
