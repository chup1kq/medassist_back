package owl.medassist_back.medAssist.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewCreateDto(
        @NotBlank String authorName,
        @Min(0) @Max(5) Integer rating,
        @NotBlank String text
) {
}
