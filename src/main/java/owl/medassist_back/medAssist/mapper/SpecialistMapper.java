package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.specialist.SpecialistDto;
import owl.medassist_back.medAssist.entity.specialist.Specialist;

@Mapper(
        componentModel = "spring",
        uses = {
                SpecializationMapper.class,
                ReviewMapper.class,
                ScheduleMapper.class
        }
)
public interface SpecialistMapper {

    SpecialistDto toDto(Specialist specialist);
}
