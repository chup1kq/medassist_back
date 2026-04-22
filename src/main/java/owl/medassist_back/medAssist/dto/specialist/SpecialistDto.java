package owl.medassist_back.medAssist.dto.specialist;

import owl.medassist_back.medAssist.dto.review.ReviewDto;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;

import java.util.List;

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

