package owl.medassist_back.medAssist.dto.medicalService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record MedicalServiceConditionIdsDto(
        @NotNull(message = "conditionIds is required")
        List<@Positive(message = "condition id must be > 0") Integer> conditionIds
) {
}

