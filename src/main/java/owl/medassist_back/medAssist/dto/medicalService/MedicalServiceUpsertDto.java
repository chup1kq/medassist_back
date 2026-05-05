package owl.medassist_back.medAssist.dto.medicalService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MedicalServiceUpsertDto(
        @NotBlank(message = "name is required")
        @Size(max = 255, message = "name length must be <= 255")
        String name,
        String description,
        String details,
        String preparation,
        @Size(max = 120, message = "url length must be <= 120")
        String url,
        @Size(max = 500, message = "photoUrl length must be <= 500")
        String photoUrl,
        List<@Positive(message = "indication id must be > 0") Integer> indicationIds,
        List<@Positive(message = "contraindication id must be > 0") Integer> contraindicationIds,
        List<@Positive(message = "specialist id must be > 0") Integer> specialistIds
) {
}

