package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.document.DocumentTypeDto;
import owl.medassist_back.medAssist.dto.document.DocumentTypeUpsertDto;
import owl.medassist_back.medAssist.entity.document.DocumentType;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {

    DocumentTypeDto toDto(DocumentType entity);

    DocumentType toEntity(DocumentTypeUpsertDto dto);
}
