package owl.medassist_back.medAssist.dto.servicePrice;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ServicePriceUpsertDto(
        @NotNull(message = "serviceId is required")
        Integer serviceId,
        @NotNull(message = "misId is required")
        @Min(value = 0, message = "misId must be >= 0")
        Integer misId,
        @NotBlank(message = "name is required")
        @Size(max = 255, message = "name length must be <= 255")
        String name,
        @NotNull(message = "price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "price must be > 0")
        BigDecimal price
) {
}

