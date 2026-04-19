package owl.medassist_back.medAssist.dto.medicalFacility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicalFacilityUpsertDto(
        @NotBlank(message = "name is required")
        @Size(max = 255, message = "name length must be <= 255")
        String name,
        @NotBlank(message = "address is required")
        @Size(max = 255, message = "address length must be <= 255")
        String address,
        String description
) {
}

