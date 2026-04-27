package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;

@Mapper(componentModel = "spring")
public interface ServicePriceMapper {

    ServicePriceDto toDto(ServicePrice servicePrice);
}
