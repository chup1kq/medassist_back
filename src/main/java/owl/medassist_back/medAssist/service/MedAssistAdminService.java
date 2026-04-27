package owl.medassist_back.medAssist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import owl.medassist_back.medAssist.entity.document.Document;
import owl.medassist_back.medAssist.entity.document.DocumentType;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;
import owl.medassist_back.medAssist.entity.schedule.Schedule;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;
import owl.medassist_back.medAssist.entity.specialist.Specialist;
import owl.medassist_back.medAssist.entity.specialist.Specialization;
import owl.medassist_back.medAssist.mapper.DocumentMapper;
import owl.medassist_back.medAssist.mapper.MedicalFacilityMapper;
import owl.medassist_back.medAssist.mapper.MedicalServiceMapper;
import owl.medassist_back.medAssist.mapper.ScheduleMapper;
import owl.medassist_back.medAssist.mapper.ServicePriceMapper;
import owl.medassist_back.medAssist.mapper.SpecialistMapper;
import owl.medassist_back.medAssist.repository.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MedAssistAdminService {

    private final MedicalServiceRepository medicalServiceRepository;
    private final ServicePriceRepository servicePriceRepository;
    private final SpecialistRepository specialistRepository;
    private final SpecializationRepository specializationRepository;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final ScheduleRepository scheduleRepository;
    private final MedicalFacilityRepository medicalFacilityRepository;
    private final SpecialistFacilityRepository specialistFacilityRepository;

    private final MedicalServiceMapper medicalServiceMapper;
    private final ServicePriceMapper servicePriceMapper;
    private final SpecialistMapper specialistMapper;
    private final DocumentMapper documentMapper;
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
        service.setName(request.name().trim());
        service.setDescription(normalize(request.description()));
        service.setDetails(normalize(request.details()));
        service.setPreparation(normalize(request.preparation()));
        service.setUrl(normalize(request.url()));
        service.setPhotoUrl(normalize(request.photoUrl()));
    }

    private void applyServicePriceChanges(ServicePrice price, ServicePriceUpsertDto request) {
        MedicalService service = medicalServiceRepository.findById(request.serviceId())
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));

        price.setService(service);
        price.setName(request.name().trim());
        price.setPrice(request.price());
    }

    private void applySpecialistChanges(Specialist specialist, SpecialistUpsertDto request) {
        specialist.setFullName(request.fullName().trim());
        specialist.setDescription(normalize(request.description()));
        specialist.setPhotoUrl(normalize(request.photoUrl()));
        specialist.setExperienceYears(request.experienceYears());
        specialist.setActive(request.active());

        Set<Specialization> resolvedSpecializations = new LinkedHashSet<>();
        if (request.specializationIds() != null) {
            specializationRepository.findAllById(request.specializationIds())
                    .forEach(resolvedSpecializations::add);
        }

        specialist.getSpecializations().clear();
        specialist.getSpecializations().addAll(resolvedSpecializations);
    }

    private void applyDocumentChanges(Document document, DocumentUpsertDto request) {
        document.setName(request.name().trim());
        document.setDescription(normalize(request.description()));

        String normalizedTypeName = normalize(request.documentType());
        if (normalizedTypeName != null) {
            DocumentType documentType = documentTypeRepository.findByNameIgnoreCase(normalizedTypeName)
                    .orElseGet(() -> {
                        DocumentType created = new DocumentType();
                        created.setName(normalizedTypeName);
                        return documentTypeRepository.save(created);
                    });
            document.setDocumentType(documentType);
        }

        document.setFileUrl(normalize(request.fileUrl()));
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
        facility.setName(request.name().trim());
        facility.setAddress(request.address().trim());
        facility.setDescription(normalize(request.description()));
    }

    private void validateServiceUrl(String url, Integer serviceId) {
        String normalizedUrl = normalize(url);
        if (normalizedUrl == null) {
            return;
        }

        boolean exists = serviceId == null
                ? medicalServiceRepository.existsByUrlIgnoreCase(normalizedUrl)
                : medicalServiceRepository.existsByUrlIgnoreCaseAndIdNot(normalizedUrl, serviceId);

        if (exists) {
            throw new BaseAppException(ExceptionName.DUPLICATE_SERVICE_URL);
        }
    }

    private String normalize(String value) {
        return value == null ? null : value.toLowerCase().trim();
    }
}
