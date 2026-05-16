package owl.medassist_back.medAssist.dto.specialist;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SpecialistUpsertDto(
        @NotBlank(message = "fullName is required")
        @Size(max = 255, message = "fullName length must be <= 255")
        String fullName,
        String description,
        String photoUrl,
        @Min(value = 0, message = "experienceYears must be >= 0")
        Integer experienceYears,
        @NotNull(message = "active is required")
        Boolean active,
        @Min(value = 0, message = "misId must be >= 0")
        Integer misId,
        List<Integer> specializationIds
) {
}

