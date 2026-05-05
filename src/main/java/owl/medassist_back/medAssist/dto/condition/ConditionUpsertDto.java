package owl.medassist_back.medAssist.dto.condition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ConditionUpsertDto(
        @NotBlank(message = "text is required")
        @Size(max = 255, message = "text length must be <= 255")
        String text
) {
}
