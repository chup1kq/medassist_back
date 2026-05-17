package owl.medassist_back.medAssist.dto.servicePrice;

import java.math.BigDecimal;

public record ServicePriceDto(
        Integer id,
        Integer misId,
        String name,
        BigDecimal price,
        Integer serviceId,
        String serviceName
) {
}

