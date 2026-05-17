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
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceConditionIdsDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityNameDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityUpsertDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityScheduleDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityScheduleUpsertDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceUpsertDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleUpsertDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceUpsertDto;
import owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityDto;
import owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityUpsertDto;
import owl.medassist_back.medAssist.dto.specialist.*;
import owl.medassist_back.medAssist.service.MedAssistAdminService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class MedAssistAdminController {

    private final MedAssistAdminService medAssistAdminService;

    @GetMapping("/services/all")
    public List<MedicalServiceNameDto> getServices() {
        return medAssistAdminService.getServicesAll();
    }
    
    @GetMapping("/services")
    public Page<MedicalServiceDto> getServices(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getServices(page, query);
    }

    @PostMapping("/services")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalServiceDto createService(@Valid @RequestBody MedicalServiceUpsertDto request) {
        return medAssistAdminService.createService(request);
    }

    @PutMapping("/services/{serviceId}")
    public MedicalServiceDto updateService(@PathVariable Integer serviceId, @Valid @RequestBody MedicalServiceUpsertDto request) {
        return medAssistAdminService.updateService(serviceId, request);
    }

    @DeleteMapping("/services/{serviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Integer serviceId) {
        medAssistAdminService.deleteService(serviceId);
    }

    @GetMapping("/services/{serviceId}")
    public MedicalServiceDto getServiceById(@PathVariable Integer serviceId) {
        return medAssistAdminService.getServiceById(serviceId);
    }

    @GetMapping("/services/{serviceId}/indications")
    public List<ConditionDto> getServiceIndications(@PathVariable Integer serviceId) {
        return medAssistAdminService.getServiceIndications(serviceId);
    }

    @PutMapping("/services/{serviceId}/indications")
    public List<ConditionDto> updateServiceIndications(
            @PathVariable Integer serviceId,
            @Valid @RequestBody MedicalServiceConditionIdsDto request
    ) {
        return medAssistAdminService.updateServiceIndications(serviceId, request);
    }

    @GetMapping("/services/{serviceId}/contraindications")
    public List<ConditionDto> getServiceContraindications(@PathVariable Integer serviceId) {
        return medAssistAdminService.getServiceContraindications(serviceId);
    }

    @PutMapping("/services/{serviceId}/contraindications")
    public List<ConditionDto> updateServiceContraindications(
            @PathVariable Integer serviceId,
            @Valid @RequestBody MedicalServiceConditionIdsDto request
    ) {
        return medAssistAdminService.updateServiceContraindications(serviceId, request);
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

    @GetMapping("/specialists/all")
    public List<SpecialistNameDto> getSpecialists() {
        return medAssistAdminService.getSpecialistsAll();
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

    @GetMapping("/specialists/{specialistId}")
    public SpecialistDto getSpecialistById(@PathVariable Integer specialistId) {
        return medAssistAdminService.getSpecialistById(specialistId);
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

    @GetMapping("/facilities/all")
    public List<MedicalFacilityNameDto> getFacilities() {
        return medAssistAdminService.getFacilitiesAll();
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

    @GetMapping("/specialist-facilities")
    public List<SpecialistFacilityDto> getSpecialistFacilities() {
        return medAssistAdminService.getSpecialistFacilities();
    }

    @PostMapping("/specialist-facilities")
    @ResponseStatus(HttpStatus.CREATED)
    public SpecialistFacilityDto createSpecialistFacility(@Valid @RequestBody SpecialistFacilityUpsertDto request) {
        return medAssistAdminService.createSpecialistFacility(request);
    }

    @PutMapping("/specialist-facilities/{specialistFacilityId}")
    public SpecialistFacilityDto updateSpecialistFacility(
            @PathVariable Integer specialistFacilityId,
            @Valid @RequestBody SpecialistFacilityUpsertDto request
    ) {
        return medAssistAdminService.updateSpecialistFacility(specialistFacilityId, request);
    }

    @DeleteMapping("/specialist-facilities/{specialistFacilityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecialistFacility(@PathVariable Integer specialistFacilityId) {
        medAssistAdminService.deleteSpecialistFacility(specialistFacilityId);
    }

    @GetMapping("/conditions")
    public Page<ConditionDto> getConditions(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistAdminService.getConditions(page, query);
    }

    @GetMapping("/conditions/all")
    public List<ConditionDto> getAllConditions() {
        return medAssistAdminService.getAllConditions();
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

    @GetMapping("/specializations/all")
    public List<SpecializationDto> getSpecializations() {
        return medAssistAdminService.getSpecializationsAll();
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

    @GetMapping("/document-types/all")
    public List<DocumentTypeDto> getDocumentTypes() {
        return medAssistAdminService.getDocumentTypesAll();
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

    @GetMapping("/facilities/{facilityId}/schedules")
    public List<MedicalFacilityScheduleDto> getFacilitySchedules(
            @PathVariable @Min(1) Integer facilityId
    ) {
        return medAssistAdminService.getFacilitySchedules(facilityId);
    }


    @PostMapping("/facilities/{facilityId}/schedules")
    @ResponseStatus(HttpStatus.CREATED)
    public MedicalFacilityScheduleDto createFacilitySchedule(
            @PathVariable @Min(1) Integer facilityId,
            @Valid @RequestBody MedicalFacilityScheduleUpsertDto scheduleDto
    ) {
        return medAssistAdminService.createFacilitySchedule(facilityId, scheduleDto);
    }


    @PutMapping("/facilities/{facilityId}/schedules")
    public MedicalFacilityScheduleDto upsertFacilitySchedule(
            @PathVariable @Min(1) Integer facilityId,
            @Valid @RequestBody MedicalFacilityScheduleUpsertDto scheduleDto
    ) {
        return medAssistAdminService.upsertFacilitySchedule(facilityId, scheduleDto);
    }


    @DeleteMapping("/facilities/{facilityId}/schedules/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFacilitySchedule(
            @PathVariable @Min(1) Integer facilityId,
            @PathVariable @Min(1) Integer scheduleId
    ) {
        medAssistAdminService.deleteFacilitySchedule(facilityId, scheduleId);
    }
}
