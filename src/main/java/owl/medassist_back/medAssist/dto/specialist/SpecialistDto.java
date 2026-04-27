package owl.medassist_back.medAssist.dto.specialist;

import lombok.Builder;
import owl.medassist_back.medAssist.dto.review.ReviewDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;

import java.util.List;

@Builder
public record SpecialistDto(
        Integer id,
        String fullName,
        String description,
        Integer experienceYears,
        String photoUrl,
        Boolean active,
        List<SpecializationDto> specializations,
        List<ScheduleDto> schedules,
        List<ReviewDto> reviews
) {
}

