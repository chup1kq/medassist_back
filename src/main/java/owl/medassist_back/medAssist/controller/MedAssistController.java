package owl.medassist_back.medAssist.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import owl.medassist_back.medAssist.dto.condition.ConditionDto;
import owl.medassist_back.medAssist.dto.document.DocumentDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.dto.search.SearchResponseDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistCardDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistDto;
import owl.medassist_back.medAssist.dto.specialist.SpecializationDto;
import owl.medassist_back.medAssist.service.MedAssistService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/med-assist")
@RequiredArgsConstructor
@Validated
public class MedAssistController {

    private final MedAssistService medAssistService;

    @GetMapping("/services")
    public List<MedicalServiceNameDto> getServiceNames(@RequestParam(required = false) String query) {
        return medAssistService.getServiceNames(query);
    }

    @GetMapping("/conditions")
    public Page<ConditionDto> getConditions(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false) String query
    ) {
        return medAssistService.getConditions(page, query);
    }

    @GetMapping("/specializations")
    public List<SpecializationDto> getSpecializations(
            @RequestParam(required = false) String query
    ) {
        return medAssistService.getSpecializations(query);
    }

    @GetMapping("/document-types")
    public List<DocumentTypeDto> getDocumentTypes(
            @RequestParam(required = false) String query
    ) {
        return medAssistService.getDocumentTypes(query);
    }

    @GetMapping("/services/{serviceUrl}")
    public MedicalServiceDto getServiceDetails(@PathVariable String serviceUrl) {
        return medAssistService.getServiceDetails(serviceUrl);
    }

    @GetMapping("/specialists")
    public Page<SpecialistCardDto> getSpecialists(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") @Min(0) int page
    ) {
        return medAssistService.getSpecialists(query, specialization, page);
    }

    @GetMapping("/specialists/{specialistId}")
    public SpecialistDto getSpecialistDetails(@PathVariable Integer specialistId) {
        return medAssistService.getSpecialistDetails(specialistId);
    }

    @GetMapping("/schedules")
    public List<ScheduleDto> getSchedules(
            @RequestParam(required = false) Integer specialistId,
            @RequestParam(required = false) Integer facilityId,
            @RequestParam(required = false) Integer dayOfWeek
    ) {
        return medAssistService.getSchedules(specialistId, facilityId, dayOfWeek);
    }

    @GetMapping("/documents")
    public Page<DocumentDto> getDocuments(
            @RequestParam(required = false) String query,
            @RequestParam(required = false, name = "documentTypeId") Integer documentTypeId,
            @RequestParam(defaultValue = "0") @Min(0) int page
    ) {
        return medAssistService.getDocuments(query, documentTypeId, page);
    }

    @GetMapping("/search")
    public SearchResponseDto search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") @Min(0) int servicePage,
            @RequestParam(defaultValue = "0") @Min(0) int specialistPage,
            @RequestParam(defaultValue = "0") @Min(0) int documentPage
    ) {
        return medAssistService.search(query, servicePage, specialistPage, documentPage);
    }
}

