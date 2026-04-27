package owl.medassist_back.medAssist.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DocumentUpsertDto(
        @NotBlank(message = "name is required")
        @Size(max = 255, message = "name length must be <= 255")
        String name,
        String description,
        @Size(max = 100, message = "documentType length must be <= 100")
        String documentType,
        @Size(max = 512, message = "fileUrl length must be <= 512")
        String fileUrl
) {
}

