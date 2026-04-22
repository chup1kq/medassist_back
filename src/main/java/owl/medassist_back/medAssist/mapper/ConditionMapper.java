package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.condition.ConditionDto;
import owl.medassist_back.medAssist.entity.indication.Condition;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    ConditionDto toDto(Condition condition);

    Condition toEntity(ConditionDto conditionDto);
}
