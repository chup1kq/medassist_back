package owl.medassist_back.medAssist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import owl.medassist_back.exception.BaseAppException;
import owl.medassist_back.exception.ExceptionName;
import owl.medassist_back.medAssist.dto.condition.ConditionDto;
import owl.medassist_back.medAssist.dto.document.DocumentDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceCardDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameDto;
import owl.medassist_back.medAssist.dto.review.ReviewDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.dto.search.SearchResponseDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistCardDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistDto;
import owl.medassist_back.medAssist.dto.specialist.SpecializationDto;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;
import owl.medassist_back.medAssist.entity.review.Review;
import owl.medassist_back.medAssist.entity.schedule.Schedule;
import owl.medassist_back.medAssist.entity.specialist.Specialist;
import owl.medassist_back.medAssist.mapper.*;
import owl.medassist_back.medAssist.repository.ConditionRepository;
import owl.medassist_back.medAssist.repository.DocumentRepository;
import owl.medassist_back.medAssist.repository.DocumentTypeRepository;
import owl.medassist_back.medAssist.repository.MedicalServiceRepository;
import owl.medassist_back.medAssist.repository.ScheduleRepository;
import owl.medassist_back.medAssist.repository.SpecialistRepository;
import owl.medassist_back.medAssist.repository.SpecializationRepository;
import owl.medassist_back.medAssist.repository.SpecialistSpecification;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedAssistService {

    @Value("${spring.page.page-size}")
    private int pageSize;

    private final MedicalServiceRepository medicalServiceRepository;
    private final SpecialistRepository specialistRepository;
    private final ScheduleRepository scheduleRepository;
    private final DocumentRepository medicalDocumentRepository;
    private final ConditionRepository conditionRepository;
    private final SpecializationRepository specializationRepository;
    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentMapper documentMapper;
    private final MedicalServiceMapper medicalServiceMapper;
    private final SpecialistMapper specialistMapper;
    private final ScheduleMapper scheduleMapper;
    private final ReviewMapper reviewMapper;
    private final SpecializationMapper specializationMapper;
    private final ConditionMapper conditionMapper;
    private final DocumentTypeMapper documentTypeMapper;


    public Page<MedicalServiceCardDto> getServices(String query, int page) {
        return medicalServiceRepository.findAllCards(query, PageRequest.of(page, pageSize));
    }

    public Page<ConditionDto> getConditions(int page, String query) {
        return conditionRepository.search(normalize(query), PageRequest.of(page, pageSize, Sort.by("text").ascending()))
                .map(conditionMapper::toDto);
    }

    public List<SpecializationDto> getSpecializations(String query) {
        return specializationRepository.search(normalize(query))
                .stream()
                .map(specializationMapper::toDto)
                .toList();
    }

    public List<DocumentTypeDto> getDocumentTypes(String query) {
        return documentTypeRepository.search(normalize(query))
                .stream()
                .map(documentTypeMapper::toDto)
                .toList();
    }

    public List<MedicalServiceNameDto> getServiceNames(String query) {
        return medicalServiceRepository.findAllNames(query);
    }

    public MedicalServiceDto getServiceDetails(String serviceUrl) {

        MedicalService service = medicalServiceRepository.findDetailedByUrl(serviceUrl)
                .orElseThrow(() -> new BaseAppException(ExceptionName.SERVICE_NOT_FOUND));  // TODO

        return medicalServiceMapper.toDto(service);
    }

    public Page<SpecialistCardDto> getSpecialists(String query, String specialization, int page) {

        Specification<Specialist> spec =
                SpecialistSpecification.searchActive(
                        normalize(query),
                        normalize(specialization)
                );

        return specialistRepository
                .findAll(spec, PageRequest.of(page, pageSize))
                .map(specialistMapper::toCardDto);
    }

    public SpecialistDto getSpecialistDetails(Integer specialistId) {

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
                        .comparing(Review::getCreatedAt,
                                Comparator.nullsLast(Comparator.naturalOrder()))
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

    public List<ScheduleDto> getSchedules(Integer specialistId, Integer facilityId, Integer dayOfWeek) {
        if (dayOfWeek != null && (dayOfWeek < 1 || dayOfWeek > 7)) {
            throw new BaseAppException(ExceptionName.INVALID_DAY_OF_WEEK);
        }

        return scheduleRepository.findByFilters(specialistId, facilityId, dayOfWeek)
                .stream()
                .map(scheduleMapper::toDto)
                .toList();
    }

    public List<DocumentDto> getDocuments(String query, Integer documentTypeId) {
        return medicalDocumentRepository.search(
                        normalize(query),
                        documentTypeId
                )
                .stream()
                .map(documentMapper::toDto)
                .toList();
    }

    public Page<DocumentDto> getDocuments(String query, Integer documentTypeId, int page) {

        return medicalDocumentRepository.search(
                        normalize(query),
                        documentTypeId,
                        PageRequest.of(page, pageSize)
                )
                .map(documentMapper::toDto);
    }

    public SearchResponseDto search(String query, int servicePage, int specialistPage, int documentPage) {
        String normalizedQuery = normalize(query);

        return new SearchResponseDto(
                getServices(normalizedQuery, servicePage).getContent(),
                getSpecialists(normalizedQuery, null, specialistPage).getContent(),
                getDocuments(normalizedQuery, null, documentPage).getContent()
        );
    }

    private String normalize(String value) {
        return (value == null || value.isBlank())
                ? ""
                : value.toLowerCase().trim();
    }
}

