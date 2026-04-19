package owl.medassist_back.medAssist.dto.document;

public record DocumentDto(
        Integer id,
        String name,
        String description,
        DocumentTypeDto documentType,
        String fileUrl
) {
}

