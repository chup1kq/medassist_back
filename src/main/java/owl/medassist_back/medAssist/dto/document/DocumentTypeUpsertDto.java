package owl.medassist_back.medAssist.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DocumentTypeUpsertDto(
    @NotBlank(message = "name is required")
    @Size(max = 100, message = "name length must be <= 100")
    String name
) {
}
