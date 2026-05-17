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
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityNameDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityUpsertDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityScheduleDto;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityScheduleUpsertDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceConditionIdsDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceUpsertDto;
import owl.medassist_back.medAssist.dto.review.ReviewDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleUpsertDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceUpsertDto;
import owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityDto;
import owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityUpsertDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistCardDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistNameDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistUpsertDto;
import owl.medassist_back.medAssist.dto.specialist.SpecializationDto;
import owl.medassist_back.medAssist.dto.specialist.SpecializationUpsertDto;
import owl.medassist_back.medAssist.entity.document.Document;
import owl.medassist_back.medAssist.entity.document.DocumentType;
import owl.medassist_back.medAssist.entity.indication.Condition;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacilitySchedule;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;
import owl.medassist_back.medAssist.entity.schedule.Schedule;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;
import owl.medassist_back.medAssist.entity.specialist.Specialist;
import owl.medassist_back.medAssist.entity.specialist.Specialization;
import owl.medassist_back.medAssist.entity.review.Review;
import owl.medassist_back.medAssist.mapper.DocumentMapper;
import owl.medassist_back.medAssist.mapper.DocumentTypeMapper;
import owl.medassist_back.medAssist.mapper.ConditionMapper;
import owl.medassist_back.medAssist.mapper.MedicalFacilityMapper;
import owl.medassist_back.medAssist.mapper.MedicalServiceMapper;
import owl.medassist_back.medAssist.mapper.ScheduleMapper;
import owl.medassist_back.medAssist.mapper.ServicePriceMapper;
import owl.medassist_back.medAssist.mapper.ReviewMapper;
import owl.medassist_back.medAssist.mapper.SpecialistFacilityMapper;
import owl.medassist_back.medAssist.mapper.SpecializationMapper;
import owl.medassist_back.medAssist.mapper.SpecialistMapper;
import owl.medassist_back.medAssist.mapper.MedicalFacilityScheduleMapper;
import owl.medassist_back.medAssist.repository.*;

import java.util.Comparator;
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
    private final MedicalFacilityScheduleRepository medicalFacilityScheduleRepository;

    private final MedicalServiceMapper medicalServiceMapper;
    private final ServicePriceMapper servicePriceMapper;
    private final SpecialistMapper specialistMapper;
    private final DocumentMapper documentMapper;
    private final ConditionMapper conditionMapper;
    private final SpecializationMapper specializationMapper;
    private final DocumentTypeMapper documentTypeMapper;
    private final ScheduleMapper scheduleMapper;
    private final ReviewMapper reviewMapper;
    private final MedicalFacilityMapper medicalFacilityMapper;
    private final SpecialistFacilityMapper specialistFacilityMapper;
    private final MedicalFacilityScheduleMapper medicalFacilityScheduleMapper;

    public MedicalServiceDto createService(MedicalServiceUpsertDto request) {
        validateServiceUrl(request.url(), null);

        MedicalService service = new MedicalService();
        applyServiceChanges(service, request);

        MedicalService saved = medicalServiceRepository.save(service);
        return medicalServiceMapper.toDto(saved);
    }

    public MedicalServiceDto updateService(Integer serviceId, MedicalServiceUpsertDto request) {
        MedicalService service = medicalServiceRepository.findById(serviceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        validateServiceUrl(request.url(), serviceId);
        applyServiceChanges(service, request);

        return medicalServiceMapper.toDto(service);
    }

    public MedicalServiceDto getServiceById(Integer serviceId) {
        MedicalService service = medicalServiceRepository.findDetailedById(serviceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        return medicalServiceMapper.toDto(service);
    }

    public List<ConditionDto> getServiceIndications(Integer serviceId) {
        return getServiceEntityById(serviceId).getIndications().stream()
                .sorted((left, right) -> left.getText().compareToIgnoreCase(right.getText()))
                .map(conditionMapper::toDto)
                .toList();
    }

    public List<ConditionDto> updateServiceIndications(Integer serviceId, MedicalServiceConditionIdsDto request) {
        MedicalService service = getServiceEntityById(serviceId);
        updateServiceConditions(service.getIndications(), request.conditionIds());
        return getServiceIndications(serviceId);
    }

    public List<ConditionDto> getServiceContraindications(Integer serviceId) {
        return getServiceEntityById(serviceId).getContraindications().stream()
                .sorted((left, right) -> left.getText().compareToIgnoreCase(right.getText()))
                .map(conditionMapper::toDto)
                .toList();
    }

    public List<ConditionDto> updateServiceContraindications(Integer serviceId, MedicalServiceConditionIdsDto request) {
        MedicalService service = getServiceEntityById(serviceId);
        updateServiceConditions(service.getContraindications(), request.conditionIds());
        return getServiceContraindications(serviceId);
    }

    public void deleteService(Integer serviceId) {
        MedicalService service = medicalServiceRepository.findById(serviceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));
        medicalServiceRepository.delete(service);
    }

    public Page<MedicalServiceDto> getServices(int page, String query) {
        return medicalServiceRepository.searchDetailed(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()))
                .map(medicalServiceMapper::toDto);
    }

    public List<MedicalServiceNameDto> getServicesAll() {
        return medicalServiceRepository.findAllName();
    }

    public Page<ServicePriceDto> getServicePrices(int page, String query) {
        return servicePriceRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("name").ascending()));
    }

    public Page<SpecialistCardDto> getSpecialists(int page, String query) {
        return specialistRepository.searchByFullName(normalize(query), PageRequest.of(page, pageSize, Sort.by("fullName").ascending()))
                .map(specialistMapper::toCardDto);
    }

    public SpecialistDto getSpecialistById(Integer specialistId) {
        Specialist specialist = specialistRepository.findDetailedById(specialistId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));

        List<SpecializationDto> specializations = specialist.getSpecializations()
                .stream()
                .map(specializationMapper::toDto)
                .toList();

        List<ScheduleDto> schedules = specialist.getSpecialistFacilities()
                .stream()
                .flatMap(sf -> sf.getSchedules().stream())
                .sorted(Comparator
                        .comparing(Schedule::getDayOfWeek)
                        .thenComparing(Schedule::getStartTime))
                .map(scheduleMapper::toDto)
                .toList();

        List<ReviewDto> reviews = specialist.getReviews()
                .stream()
                .sorted(Comparator
                        .comparing(Review::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()))
                        .reversed())
                .map(reviewMapper::toDto)
                .toList();

        return SpecialistDto.builder()
                .id(specialist.getId())
                .fullName(specialist.getFullName())
                .description(specialist.getDescription())
                .experienceYears(specialist.getExperienceYears())
                .photoUrl(specialist.getPhotoUrl())
                .active(specialist.getActive())
                .specializations(specializations)
                .schedules(schedules)
                .reviews(reviews)
                .build();
    }

    public List<SpecialistNameDto> getSpecialistsAll() {
        return specialistRepository.findAllName();
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

    public List<MedicalFacilityNameDto> getFacilitiesAll() {
        return medicalFacilityRepository.findAllName();
    }

    public List<SpecialistFacilityDto> getSpecialistFacilities() {
        return specialistFacilityRepository.findAllCards();
    }

    public SpecialistFacilityDto createSpecialistFacility(SpecialistFacilityUpsertDto request) {
        validateSpecialistFacilityRequest(request.specialistId(), request.facilityId(), null);

        SpecialistFacility specialistFacility = new SpecialistFacility();
        applySpecialistFacilityChanges(specialistFacility, request);

        SpecialistFacility saved = specialistFacilityRepository.save(specialistFacility);
        return specialistFacilityMapper.toDto(saved);
    }

    public SpecialistFacilityDto updateSpecialistFacility(Integer specialistFacilityId, SpecialistFacilityUpsertDto request) {
        SpecialistFacility specialistFacility = specialistFacilityRepository.findDetailedById(specialistFacilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_FACILITY_NOT_FOUND));

        validateSpecialistFacilityRequest(request.specialistId(), request.facilityId(), specialistFacilityId);
        applySpecialistFacilityChanges(specialistFacility, request);

        return specialistFacilityMapper.toDto(specialistFacility);
    }

    public void deleteSpecialistFacility(Integer specialistFacilityId) {
        SpecialistFacility specialistFacility = specialistFacilityRepository.findById(specialistFacilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_FACILITY_NOT_FOUND));
        specialistFacilityRepository.delete(specialistFacility);
    }

    public Page<ConditionDto> getConditions(int page, String query) {
        return conditionRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("text").ascending()))
                .map(conditionMapper::toDto);
    }

    public List<ConditionDto> getAllConditions() {
        return conditionRepository.findAll(Sort.by("text").ascending())
                .stream()
                .map(conditionMapper::toDto)
                .toList();
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

    public List<SpecializationDto> getSpecializationsAll() {
        return specializationRepository.findAll(Sort.by("name").ascending())
                .stream()
                .map(specializationMapper::toDto)
                .toList();
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

    public List<DocumentTypeDto> getDocumentTypesAll() {
        return documentTypeRepository.search(normalize(null))
                .stream()
                .map(documentTypeMapper::toDto)
                .toList();
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
        if (request.misId() != null) {
            service.setMisId(request.misId());
        }

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
            service.getSpecialists().clear();
            service.getSpecialists().addAll(specialists);
        } else {
            service.getSpecialists().clear();
        }
    }

    private MedicalService getServiceEntityById(Integer serviceId) {
        return medicalServiceRepository.findById(serviceId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));
    }

    private void updateServiceConditions(Set<Condition> target, List<Integer> conditionIds) {
        Set<Integer> requestedIds = new LinkedHashSet<>(conditionIds);
        List<Condition> conditions = conditionRepository.findAllById(requestedIds);

        if (conditions.size() != requestedIds.size()) {
            throw new BaseAppException(ExceptionName.CONDITION_NOT_FOUND);
        }

        target.clear();
        target.addAll(conditions);
    }

    private void applyServicePriceChanges(ServicePrice price, ServicePriceUpsertDto request) {
        MedicalService service = medicalServiceRepository.findById(request.serviceId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        price.setService(service);
        price.setMisId(request.misId());
        price.setName(normalizeOriginCase(request.name()));
        price.setPrice(request.price());
    }

    private void applySpecialistChanges(Specialist specialist, SpecialistUpsertDto request) {
        specialist.setFullName(normalizeOriginCase(request.fullName()));
        specialist.setDescription(normalizeOriginCase(request.description()));
        specialist.setPhotoUrl(normalizeOriginCase(request.photoUrl()));
        specialist.setExperienceYears(request.experienceYears());
        specialist.setActive(request.active());
        if (request.misId() != null) {
            specialist.setMisId(request.misId());
        }

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

    private void applySpecialistFacilityChanges(SpecialistFacility specialistFacility, SpecialistFacilityUpsertDto request) {
        Specialist specialist = specialistRepository.findById(request.specialistId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SPECIALIST_NOT_FOUND));
        MedicalFacility facility = medicalFacilityRepository.findById(request.facilityId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));

        specialistFacility.setSpecialist(specialist);
        specialistFacility.setFacility(facility);
    }

    private void validateSpecialistFacilityRequest(Integer specialistId, Integer facilityId, Integer specialistFacilityId) {
        if (specialistId == null || facilityId == null) {
            throw new BaseAppException(ExceptionName.SPECIALIST_FACILITY_TARGET_REQUIRED);
        }

        specialistFacilityRepository.findBySpecialistIdAndFacilityId(specialistId, facilityId)
                .filter(found -> !found.getId().equals(specialistFacilityId))
                .ifPresent(found -> {
                    throw new BaseAppException(ExceptionName.DUPLICATE_SPECIALIST_FACILITY);
                });
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
    
    @Transactional(readOnly = true)
    public List<MedicalFacilityScheduleDto> getFacilitySchedules(Integer facilityId) {
        medicalFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));

        List<MedicalFacilitySchedule> schedules = medicalFacilityScheduleRepository.findByFacilityId(facilityId);

        return schedules.stream()
                .map(medicalFacilityScheduleMapper::toDto)
                .toList();
    }

    public MedicalFacilityScheduleDto createFacilitySchedule(Integer facilityId, MedicalFacilityScheduleUpsertDto scheduleDto) {
        MedicalFacility facility = medicalFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));

        validateFacilityScheduleDto(scheduleDto);

        medicalFacilityScheduleRepository.findByFacilityIdAndDayOfWeek(facilityId, scheduleDto.dayOfWeek())
                .ifPresent(existing -> {
                    throw new BaseAppException(ExceptionName.FACILITY_SCHEDULE_ALREADY_EXISTS);
                });

        MedicalFacilitySchedule schedule = new MedicalFacilitySchedule();
        schedule.setMedicalFacility(facility);
        schedule.setDayOfWeek(scheduleDto.dayOfWeek());
        schedule.setStartTime(scheduleDto.startTime());
        schedule.setEndTime(scheduleDto.endTime());
        schedule.setIsClosed(scheduleDto.isClosed());
        schedule.setIs24Hours(scheduleDto.is24Hours());

        schedule = medicalFacilityScheduleRepository.save(schedule);

        return medicalFacilityScheduleMapper.toDto(schedule);
    }
    
    public MedicalFacilityScheduleDto upsertFacilitySchedule(Integer facilityId, MedicalFacilityScheduleUpsertDto scheduleDto) {
        MedicalFacility facility = medicalFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.FACILITY_NOT_FOUND));

        validateFacilityScheduleDto(scheduleDto);

        MedicalFacilitySchedule schedule = medicalFacilityScheduleRepository
                .findByFacilityIdAndDayOfWeek(facilityId, scheduleDto.dayOfWeek())
                .orElse(new MedicalFacilitySchedule());

        schedule.setMedicalFacility(facility);
        schedule.setDayOfWeek(scheduleDto.dayOfWeek());
        schedule.setStartTime(scheduleDto.startTime());
        schedule.setEndTime(scheduleDto.endTime());
        schedule.setIsClosed(scheduleDto.isClosed());
        schedule.setIs24Hours(scheduleDto.is24Hours());

        schedule = medicalFacilityScheduleRepository.save(schedule);

        return medicalFacilityScheduleMapper.toDto(schedule);
    }
    
    public void deleteFacilitySchedule(Integer facilityId, Integer scheduleId) {
        MedicalFacilitySchedule schedule = medicalFacilityScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SCHEDULE_NOT_FOUND));

        if (!schedule.getMedicalFacility().getId().equals(facilityId)) {
            throw new BaseAppException(ExceptionName.SCHEDULE_NOT_FOUND);
        }

        medicalFacilityScheduleRepository.delete(schedule);
    }
    
    private void validateFacilityScheduleDto(MedicalFacilityScheduleUpsertDto scheduleDto) {
        boolean isClosed = Boolean.TRUE.equals(scheduleDto.isClosed());
        boolean is24Hours = Boolean.TRUE.equals(scheduleDto.is24Hours());

        if (isClosed && is24Hours) {
            throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_DATA);
        }

        if (isClosed || is24Hours) {
            if (scheduleDto.startTime() != null || scheduleDto.endTime() != null) {
                throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_DATA);
            }
        } else {
            if (scheduleDto.startTime() == null || scheduleDto.endTime() == null) {
                throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_DATA);
            }

            if (!scheduleDto.startTime().isBefore(scheduleDto.endTime())) {
                throw new BaseAppException(ExceptionName.INVALID_SCHEDULE_DATA);
            }
        }
    }
}
