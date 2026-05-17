package owl.medassist_back.medAssist.dto.specialist;

import java.util.List;

public record SpecialistCardDto(
        Integer id,
        String fullName,
        String description,
        Integer experienceYears,
        String photoUrl,
        Integer misId,
        List<SpecializationDto> specializations
) {
}

