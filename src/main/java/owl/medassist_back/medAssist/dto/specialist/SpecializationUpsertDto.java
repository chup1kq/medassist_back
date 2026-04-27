package owl.medassist_back.medAssist.dto.specialist;

import jakarta.validation.constraints.NotBlank;

public record SpecializationUpsertDto(
        @NotBlank(message = "name is required")
        String name
) {
}
