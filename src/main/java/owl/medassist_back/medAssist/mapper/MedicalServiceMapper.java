package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceCardDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceDto;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;

@Mapper(
        componentModel = "spring",
        uses = {
              ServicePriceMapper.class,
              ConditionMapper.class,
              SpecialistMapper.class,
              ReviewMapper.class
        }
)
public interface MedicalServiceMapper {

    MedicalServiceDto toDto(MedicalService medicalService);

    MedicalServiceCardDto toCardDto(MedicalService medicalService);
}
