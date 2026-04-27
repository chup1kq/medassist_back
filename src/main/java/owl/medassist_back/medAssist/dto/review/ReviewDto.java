package owl.medassist_back.medAssist.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ReviewDto(
        @NotBlank String authorName,
        @Min(0) @Max(5) Integer rating,
        @NotBlank String text,
        LocalDateTime createdAt
) {
}

