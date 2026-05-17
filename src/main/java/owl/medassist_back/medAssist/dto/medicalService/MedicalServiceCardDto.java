package owl.medassist_back.medAssist.dto.medicalService;

public record MedicalServiceCardDto(
        Integer id,
        Integer misId,
        String name,
        String description,
        String url,
        String photoUrl
) {
}

