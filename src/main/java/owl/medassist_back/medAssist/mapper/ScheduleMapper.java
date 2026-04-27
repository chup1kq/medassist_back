package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.entity.schedule.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleDto toDto(Schedule schedule);
}
