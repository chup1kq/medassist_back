package owl.medassist_back.medAssist.dto.servicePrice;

import java.math.BigDecimal;

public record ServicePriceDto(
        Integer id,
        String name,
        BigDecimal price
) {
}

