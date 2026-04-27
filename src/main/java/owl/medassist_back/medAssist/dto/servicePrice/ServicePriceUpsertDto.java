package owl.medassist_back.medAssist.dto.servicePrice;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ServicePriceUpsertDto(
        @NotNull(message = "serviceId is required")
        Integer serviceId,
        @NotBlank(message = "name is required")
        @Size(max = 255, message = "name length must be <= 255")
        String name,
        @NotNull(message = "price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "price must be > 0")
        BigDecimal price
) {
}

