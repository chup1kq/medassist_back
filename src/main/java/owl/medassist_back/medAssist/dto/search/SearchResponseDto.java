package owl.medassist_back.medAssist.dto.search;

import owl.medassist_back.medAssist.dto.document.DocumentDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceCardDto;
import owl.medassist_back.medAssist.dto.specialist.SpecialistCardDto;

import java.util.List;

public record SearchResponseDto(
        List<MedicalServiceCardDto> services,
        List<SpecialistCardDto> specialists,
        List<DocumentDto> documents
) {
}

