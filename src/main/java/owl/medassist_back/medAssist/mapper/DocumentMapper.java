package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import owl.medassist_back.medAssist.dto.document.DocumentDto;
import owl.medassist_back.medAssist.entity.document.Document;

@Mapper(
        componentModel = "spring",
        uses = DocumentTypeMapper.class
)
public interface DocumentMapper {

    DocumentDto toDto(Document document);

    Document toEntity(DocumentDto dto);
}
