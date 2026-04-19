package owl.medassist_back.medAssist.dto.medicalService;

import owl.medassist_back.medAssist.dto.condition.ConditionDto;
import owl.medassist_back.medAssist.dto.review.ReviewDto;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;

import java.util.List;

public record MedicalServiceDto(
        Integer id,
        String name,
        String description,
        String details,
        String preparation,
        String url,
        String photoUrl,
        List<ServicePriceDto> prices,
        List<ConditionDto> indications,
        List<ConditionDto> contraindications,
        List<ReviewDto> reviews
) {
}

