package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import owl.medassist_back.medAssist.dto.schedule.ScheduleDto;
import owl.medassist_back.medAssist.entity.schedule.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "specialistFacility.specialist.id", target = "specialistId")
    @Mapping(source = "specialistFacility.specialist.fullName", target = "specialistName")
    @Mapping(source = "specialistFacility.facility.id", target = "facilityId")
    @Mapping(source = "specialistFacility.facility.name", target = "facilityName")
    @Mapping(source = "dayOfWeek", target = "dayOfWeek")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    ScheduleDto toDto(Schedule schedule);
}
