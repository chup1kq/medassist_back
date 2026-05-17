package owl.medassist_back.medAssist.dto.specialist;

import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;

import java.util.Set;

public record SpecialistServicePricesDto(
        Integer serviceId,
        Set<ServicePriceDto> prices
) {
}

