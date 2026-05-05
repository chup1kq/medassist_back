package owl.medassist_back.medAssist.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.medAssist.dto.condition.ConditionDto;
import owl.medassist_back.medAssist.dto.condition.ConditionUpsertDto;
import owl.medassist_back.medAssist.dto.document.DocumentDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeUpsertDto;
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
import owl.medassist_back.medAssist.dto.specialist.SpecializationDto;
import owl.medassist_back.medAssist.dto.specialist.SpecializationUpsertDto;
import owl.medassist_back.medAssist.service.MedAssistAdminService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MedAssistAdminController {

    private final MedAssistAdminService medAssistAdminService;

    @GetMapping("/services")
    public Page<MedicalServiceCardDto> getServices(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getServices(page, query);
    }

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

    @GetMapping("/service-prices")
    public Page<ServicePriceDto> getServicePrices(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getServicePrices(page, query);
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

    @GetMapping("/specialists")
    public Page<SpecialistCardDto> getSpecialists(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getSpecialists(page, query);
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

    @GetMapping("/documents")
    public Page<DocumentDto> getDocuments(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getDocuments(page, query);
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

    @GetMapping("/schedules")
    public Page<ScheduleDto> getSchedules(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, name = "specialistQuery") String specialistQuery,
            @RequestParam(required = false, name = "facilityQuery") String facilityQuery
    ) {
        return medAssistAdminService.getSchedules(page, specialistQuery, facilityQuery);
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

    @GetMapping("/facilities")
    public Page<MedicalFacilityDto> getFacilities(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getFacilities(page, query);
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

    @GetMapping("/conditions")
    public Page<ConditionDto> getConditions(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getConditions(page, query);
    }

    @PostMapping("/conditions")
    @ResponseStatus(HttpStatus.CREATED)
    public ConditionDto createCondition(@Valid @RequestBody ConditionUpsertDto request) {
        return medAssistAdminService.createCondition(request);
    }

    @PutMapping("/conditions/{conditionId}")
    public ConditionDto updateCondition(@PathVariable Integer conditionId, @Valid @RequestBody ConditionUpsertDto request) {
        return medAssistAdminService.updateCondition(conditionId, request);
    }

    @DeleteMapping("/conditions/{conditionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCondition(@PathVariable Integer conditionId) {
        medAssistAdminService.deleteCondition(conditionId);
    }

    @GetMapping("/specializations")
    public Page<SpecializationDto> getSpecializations(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getSpecializations(page, query);
    }

    @PostMapping("/specializations")
    @ResponseStatus(HttpStatus.CREATED)
    public SpecializationDto createSpecialization(@Valid @RequestBody SpecializationUpsertDto request) {
        return medAssistAdminService.createSpecialization(request);
    }

    @PutMapping("/specializations/{specializationId}")
    public SpecializationDto updateSpecialization(@PathVariable Integer specializationId, @Valid @RequestBody SpecializationUpsertDto request) {
        return medAssistAdminService.updateSpecialization(specializationId, request);
    }

    @DeleteMapping("/specializations/{specializationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialization(@PathVariable Integer specializationId) {
        medAssistAdminService.deleteSpecialization(specializationId);
    }

    @GetMapping("/document-types")
    public Page<DocumentTypeDto> getDocumentTypes(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getDocumentTypes(page, query);
    }

    @PostMapping("/document-types")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentTypeDto createDocumentType(@Valid @RequestBody DocumentTypeUpsertDto request) {
        return medAssistAdminService.createDocumentType(request);
    }

    @PutMapping("/document-types/{documentTypeId}")
    public DocumentTypeDto updateDocumentType(@PathVariable Integer documentTypeId, @Valid @RequestBody DocumentTypeUpsertDto request) {
        return medAssistAdminService.updateDocumentType(documentTypeId, request);
    }

    @DeleteMapping("/document-types/{documentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocumentType(@PathVariable Integer documentTypeId) {
        medAssistAdminService.deleteDocumentType(documentTypeId);
    }
}
