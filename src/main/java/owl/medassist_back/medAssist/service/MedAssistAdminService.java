package owl.medassist_back.medAssist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import owl.medassist_back.medAssist.dto.condition.ConditionDto;
import owl.medassist_back.medAssist.dto.condition.ConditionUpsertDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeUpsertDto;
import owl.medassist_back.exception.BaseAppException;
import owl.medassist_back.exception.ExceptionName;
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
import owl.medassist_back.medAssist.dto.specialist.SpecializationDto;
import owl.medassist_back.medAssist.dto.specialist.SpecializationUpsertDto;
import owl.medassist_back.medAssist.entity.document.Document;
import owl.medassist_back.medAssist.entity.document.DocumentType;
import owl.medassist_back.medAssist.entity.indication.Condition;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;
import owl.medassist_back.medAssist.entity.schedule.Schedule;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;
import owl.medassist_back.medAssist.entity.specialist.Specialist;
import owl.medassist_back.medAssist.entity.specialist.Specialization;
import owl.medassist_back.medAssist.mapper.DocumentMapper;
import owl.medassist_back.medAssist.mapper.DocumentTypeMapper;
import owl.medassist_back.medAssist.mapper.ConditionMapper;
import owl.medassist_back.medAssist.mapper.MedicalFacilityMapper;
import owl.medassist_back.medAssist.mapper.MedicalServiceMapper;
import owl.medassist_back.medAssist.mapper.ScheduleMapper;
import owl.medassist_back.medAssist.mapper.ServicePriceMapper;
import owl.medassist_back.medAssist.mapper.SpecializationMapper;
import owl.medassist_back.medAssist.mapper.SpecialistMapper;
import owl.medassist_back.medAssist.repository.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MedAssistAdminService {

    @Value("${spring.page.page-size}")
    private int pageSize;

    private final MedicalServiceRepository medicalServiceRepository;
    private final ServicePriceRepository servicePriceRepository;
    private final SpecialistRepository specialistRepository;
    private final SpecializationRepository specializationRepository;
    private final ConditionRepository conditionRepository;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ScheduleRepository scheduleRepository;
    private final MedicalFacilityRepository medicalFacilityRepository;
    private final SpecialistFacilityRepository specialistFacilityRepository;

    private final MedicalServiceMapper medicalServiceMapper;
    private final ServicePriceMapper servicePriceMapper;
    private final SpecialistMapper specialistMapper;
    private final DocumentMapper documentMapper;
    private final ConditionMapper conditionMapper;
    private final SpecializationMapper specializationMapper;
    private final DocumentTypeMapper documentTypeMapper;
    private final ScheduleMapper scheduleMapper;
    private final MedicalFacilityMapper medicalFacilityMapper;

    public MedicalServiceCardDto createService(MedicalServiceUpsertDto request) {
        validateServiceUrl(request.url(), null);

        MedicalService service = new MedicalService();
        applyServiceChanges(service, request);

        MedicalService saved = medicalServiceRepository.save(service);
        return medicalServiceMapper.toCardDto(saved);
    }

    public MedicalServiceCardDto updateService(Integer serviceId, MedicalServiceUpsertDto request) {
        MedicalService service = medicalServiceRepository.findById(serviceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        validateServiceUrl(request.url(), serviceId);
        applyServiceChanges(service, request);

        return medicalServiceMapper.toCardDto(service);
    }

    public void deleteService(Integer serviceId) {
        MedicalService service = medicalServiceRepository.findById(serviceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));
        medicalServiceRepository.delete(service);
    }

    public Page<MedicalServiceCardDto> getServices(int page, String query) {
        return medicalServiceRepository.findAllCards(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()));
    }

    public Page<ServicePriceDto> getServicePrices(int page, String query) {
        return servicePriceRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()))
                .map(servicePriceMapper::toDto);
    }

    public Page<SpecialistCardDto> getSpecialists(int page, String query) {
        return specialistRepository.searchByFullName(normalize(query), PageRequest.of(page, pageSize, Sort.by("fullName").ascending()))
                .map(specialistMapper::toCardDto);
    }

    public Page<DocumentDto> getDocuments(int page, String query) {
        return documentRepository.search(normalize(query), null, PageRequest.of(page, pageSize, Sort.by("name").ascending()))
                .map(documentMapper::toDto);
    }

    public Page<ScheduleDto> getSchedules(int page, String specialistQuery, String facilityQuery) {
        return scheduleRepository.search(normalize(specialistQuery), normalize(facilityQuery), PageRequest.of(page, pageSize))
                .map(scheduleMapper::toDto);
    }

    public Page<MedicalFacilityDto> getFacilities(int page, String query) {
        return medicalFacilityRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()))
                .map(medicalFacilityMapper::toDto);
    }

    public Page<ConditionDto> getConditions(int page, String query) {
        return conditionRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("text").ascending()))
                .map(conditionMapper::toDto);
    }

    public ConditionDto createCondition(ConditionUpsertDto request) {
        String normalizedText = normalizeOriginCase(request.text());
        if (normalizedText.isBlank()) {
            throw new BaseAppException(ExceptionName.CONDITION_NOT_FOUND);
        }
        if (conditionRepository.existsByTextIgnoreCase(normalizedText)) {
            throw new BaseAppException(ExceptionName.DUPLICATE_CONDITION_TEXT);
        }

        Condition condition = new Condition();
        condition.setText(normalizedText);
        return conditionMapper.toDto(conditionRepository.save(condition));
    }

    public ConditionDto updateCondition(Integer conditionId, ConditionUpsertDto request) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.CONDITION_NOT_FOUND));

        String normalizedText = normalizeOriginCase(request.text());
        if (normalizedText.isBlank()) {
            throw new BaseAppException(ExceptionName.CONDITION_NOT_FOUND);
        }

        conditionRepository.findByTextIgnoreCase(normalizedText)
                .filter(found -> !found.getId().equals(conditionId))
                .ifPresent(found -> {
                    throw new BaseAppException(ExceptionName.DUPLICATE_CONDITION_TEXT);
                });

        condition.setText(normalizedText);
        return conditionMapper.toDto(condition);
    }

    public void deleteCondition(Integer conditionId) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.CONDITION_NOT_FOUND));
        conditionRepository.delete(condition);
    }

    public Page<SpecializationDto> getSpecializations(int page, String query) {
        return specializationRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()))
                .map(specializationMapper::toDto);
    }

    public SpecializationDto createSpecialization(SpecializationUpsertDto request) {
        String normalizedName = normalizeOriginCase(request.name());
        if (normalizedName.isBlank()) {
            throw new BaseAppException(ExceptionName.SPECIALIZATION_NOT_FOUND);
        }
        if (specializationRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new BaseAppException(ExceptionName.DUPLICATE_SPECIALIZATION_NAME);
        }

        Specialization specialization = new Specialization();
        specialization.setName(normalizedName);
        return specializationMapper.toDto(specializationRepository.save(specialization));
    }

    public SpecializationDto updateSpecialization(Integer specializationId, SpecializationUpsertDto request) {
        Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIZATION_NOT_FOUND));

        String normalizedName = normalizeOriginCase(request.name());
        if (normalizedName.isBlank()) {
            throw new BaseAppException(ExceptionName.SPECIALIZATION_NOT_FOUND);
        }

        specializationRepository.findByNameIgnoreCase(normalizedName)
                .filter(found -> !found.getId().equals(specializationId))
                .ifPresent(found -> {
                    throw new BaseAppException(ExceptionName.DUPLICATE_SPECIALIZATION_NAME);
                });

        specialization.setName(normalizedName);
        return specializationMapper.toDto(specialization);
    }

    public void deleteSpecialization(Integer specializationId) {
        Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIZATION_NOT_FOUND));
        specializationRepository.delete(specialization);
    }

    public Page<DocumentTypeDto> getDocumentTypes(int page, String query) {
        return documentTypeRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()))
                .map(documentTypeMapper::toDto);
    }

    public DocumentTypeDto createDocumentType(DocumentTypeUpsertDto request) {
        String normalizedName = normalizeOriginCase(request.name());
        if (normalizedName.isBlank()) {
            throw new BaseAppException(ExceptionName.DOCUMENT_TYPE_NOT_FOUND);
        }
        if (documentTypeRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new BaseAppException(ExceptionName.DUPLICATE_DOCUMENT_TYPE_NAME);
        }

        DocumentType documentType = new DocumentType();
        documentType.setName(normalizedName);
        return documentTypeMapper.toDto(documentTypeRepository.save(documentType));
    }

    public DocumentTypeDto updateDocumentType(Integer documentTypeId, DocumentTypeUpsertDto request) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.DOCUMENT_TYPE_NOT_FOUND));

        String normalizedName = normalizeOriginCase(request.name());
        if (normalizedName.isBlank()) {
            throw new BaseAppException(ExceptionName.DOCUMENT_TYPE_NOT_FOUND);
        }

        documentTypeRepository.findByNameIgnoreCase(normalizedName)
                .filter(found -> !found.getId().equals(documentTypeId))
                .ifPresent(found -> {
                    throw new BaseAppException(ExceptionName.DUPLICATE_DOCUMENT_TYPE_NAME);
                });

        documentType.setName(normalizedName);
        return documentTypeMapper.toDto(documentType);
    }

    public void deleteDocumentType(Integer documentTypeId) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.DOCUMENT_TYPE_NOT_FOUND));
        documentTypeRepository.delete(documentType);
    }

    public ServicePriceDto createServicePrice(ServicePriceUpsertDto request) {
        ServicePrice price = new ServicePrice();
        applyServicePriceChanges(price, request);

        ServicePrice saved = servicePriceRepository.save(price);
        return servicePriceMapper.toDto(saved);
    }

    public ServicePriceDto updateServicePrice(Integer priceId, ServicePriceUpsertDto request) {
        ServicePrice price = servicePriceRepository.findById(priceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_PRICE_NOT_FOUND));

        applyServicePriceChanges(price, request);
        return servicePriceMapper.toDto(price);
    }

    public void deleteServicePrice(Integer priceId) {
        ServicePrice price = servicePriceRepository.findById(priceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_PRICE_NOT_FOUND));
        servicePriceRepository.delete(price);
    }

    public SpecialistCardDto createSpecialist(SpecialistUpsertDto request) {
        Specialist specialist = new Specialist();
        applySpecialistChanges(specialist, request);

        Specialist saved = specialistRepository.save(specialist);
        return specialistMapper.toCardDto(saved);
    }

    public SpecialistCardDto updateSpecialist(Integer specialistId, SpecialistUpsertDto request) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));

        applySpecialistChanges(specialist, request);
        return specialistMapper.toCardDto(specialist);
    }

    public void deleteSpecialist(Integer specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));
        specialistRepository.delete(specialist);
    }

    public DocumentDto createDocument(DocumentUpsertDto request) {
        Document document = new Document();
        applyDocumentChanges(document, request);

        Document saved = documentRepository.save(document);
        return documentMapper.toDto(saved);
    }

    public DocumentDto updateDocument(Integer documentId, DocumentUpsertDto request) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.DOCUMENT_NOT_FOUND));

        applyDocumentChanges(document, request);
        return documentMapper.toDto(document);
    }

    public void deleteDocument(Integer documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.DOCUMENT_NOT_FOUND));
        documentRepository.delete(document);
    }

    public ScheduleDto createSchedule(ScheduleUpsertDto request) {
        Schedule schedule = new Schedule();
        applyScheduleChanges(schedule, request);

        Schedule saved = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(saved);
    }

    public ScheduleDto updateSchedule(Integer scheduleId, ScheduleUpsertDto request) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SCHEDULE_NOT_FOUND));

        applyScheduleChanges(schedule, request);
        return scheduleMapper.toDto(schedule);
    }

    public void deleteSchedule(Integer scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SCHEDULE_NOT_FOUND));
        scheduleRepository.delete(schedule);
    }

    public MedicalFacilityDto createFacility(MedicalFacilityUpsertDto request) {
        MedicalFacility facility = new MedicalFacility();
        applyFacilityChanges(facility, request);

        MedicalFacility saved = medicalFacilityRepository.save(facility);
        return medicalFacilityMapper.toDto(saved);
    }

    public MedicalFacilityDto updateFacility(Integer facilityId, MedicalFacilityUpsertDto request) {
        MedicalFacility facility = medicalFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));

        applyFacilityChanges(facility, request);
        return medicalFacilityMapper.toDto(facility);
    }

    public void deleteFacility(Integer facilityId) {
        MedicalFacility facility = medicalFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));
        medicalFacilityRepository.delete(facility);
    }

    private void applyServiceChanges(MedicalService service, MedicalServiceUpsertDto request) {
        service.setName(normalizeOriginCase(request.name()));
        service.setDescription(normalizeOriginCase(request.description()));
        service.setDetails(normalizeOriginCase(request.details()));
        service.setPreparation(normalizeOriginCase(request.preparation()));
        service.setUrl(normalizeOriginCase(request.url()));
        service.setPhotoUrl(normalizeOriginCase(request.photoUrl()));

        if (request.indicationIds() != null) {
            List<Condition> indications = conditionRepository.findAllById(request.indicationIds());
            if (indications.size() != request.indicationIds().size()) {
                throw new BaseAppException(ExceptionName.CONDITION_NOT_FOUND);
            }
            service.getIndications().clear();
            service.getIndications().addAll(indications);
        }

        if (request.contraindicationIds() != null) {
            List<Condition> contraindications = conditionRepository.findAllById(request.contraindicationIds());
            if (contraindications.size() != request.contraindicationIds().size()) {
                throw new BaseAppException(ExceptionName.CONDITION_NOT_FOUND);
            }
            service.getContraindications().clear();
            service.getContraindications().addAll(contraindications);
        }

        if (request.specialistIds() != null && !request.specialistIds().isEmpty()) {
            List<Specialist> specialists = specialistRepository.findAllById(request.specialistIds());
            if (specialists.size() != request.specialistIds().size()) {
                throw new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND);
            }
        }
    }

    private void applyServicePriceChanges(ServicePrice price, ServicePriceUpsertDto request) {
        MedicalService service = medicalServiceRepository.findById(request.serviceId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        price.setService(service);
        price.setName(normalizeOriginCase(request.name()));
        price.setPrice(request.price());
    }

    private void applySpecialistChanges(Specialist specialist, SpecialistUpsertDto request) {
        specialist.setFullName(normalizeOriginCase(request.fullName()));
        specialist.setDescription(normalizeOriginCase(request.description()));
        specialist.setPhotoUrl(normalizeOriginCase(request.photoUrl()));
        specialist.setExperienceYears(request.experienceYears());
        specialist.setActive(request.active());

        Set<Specialization> resolvedSpecializations = new LinkedHashSet<>();
        if (request.specializationIds() != null) {
            resolvedSpecializations.addAll(specializationRepository.findAllById(request.specializationIds()));
        }

        specialist.getSpecializations().clear();
        specialist.getSpecializations().addAll(resolvedSpecializations);
    }

    private void applyDocumentChanges(Document document, DocumentUpsertDto request) {
        document.setName(normalizeOriginCase(request.name()));
        document.setDescription(normalizeOriginCase(request.description()));

        String normalizedTypeName = normalize(request.documentType());
        if (!normalizedTypeName.isBlank()) {
            String originTypeName = normalizeOriginCase(request.documentType());
            DocumentType documentType = documentTypeRepository.findByNameIgnoreCase(normalizedTypeName)
                    .orElseGet(() -> {
                        DocumentType created = new DocumentType();
                        created.setName(originTypeName);
                        return documentTypeRepository.save(created);
                    });
            document.setDocumentType(documentType);
        }

        document.setFileUrl(normalizeOriginCase(request.fileUrl()));
    }

    private void applyScheduleChanges(Schedule schedule, ScheduleUpsertDto request) {
        if (!request.endTime().isAfter(request.startTime())) {
            throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_INTERVAL);
        }

        SpecialistFacility specialistFacility = resolveSpecialistFacility(request);

        schedule.setSpecialistFacility(specialistFacility);
        schedule.setDayOfWeek(request.dayOfWeek());
        schedule.setStartTime(request.startTime());
        schedule.setEndTime(request.endTime());
    }

    private SpecialistFacility resolveSpecialistFacility(ScheduleUpsertDto request) {
        if (request.specialistFacilityId() != null) {
            return specialistFacilityRepository.findById(request.specialistFacilityId())
                    .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_FACILITY_NOT_FOUND));
        }

        if (request.specialistId() == null || request.facilityId() == null) {
            throw new BaseAppException(ExceptionName.SCHEDULE_TARGET_REQUIRED);
        }

        specialistRepository.findById(request.specialistId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));
        medicalFacilityRepository.findById(request.facilityId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));

        return specialistFacilityRepository.findBySpecialistIdAndFacilityId(request.specialistId(), request.facilityId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_FACILITY_NOT_FOUND));
    }

    private void applyFacilityChanges(MedicalFacility facility, MedicalFacilityUpsertDto request) {
        facility.setName(normalizeOriginCase(request.name()));
        facility.setAddress(normalizeOriginCase(request.address()));
        facility.setDescription(normalizeOriginCase(request.description()));
    }

    private void validateServiceUrl(String url, Integer serviceId) {
        String normalizedUrl = normalize(url);
        if (normalizedUrl.isBlank()) {
            return;
        }

        boolean exists = serviceId == null
                ? medicalServiceRepository.existsByUrlIgnoreCase(normalizedUrl)
                : medicalServiceRepository.existsByUrlIgnoreCaseAndIdNot(normalizedUrl, serviceId);

        if (exists) {
            throw new BaseAppException(ExceptionName.DUPLICATE_SERVICE_URL);
        }
    }

    private String normalizeOriginCase(String value) {
        return (value == null || value.isBlank())
                ? ""
                : value.trim();
    }
    
    private String normalize(String value) {
        return (value == null || value.isBlank())
                ? ""
                : value.toLowerCase().trim();
    }
}
