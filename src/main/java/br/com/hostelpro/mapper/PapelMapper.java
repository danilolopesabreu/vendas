package br.com.hostelpro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.hostelpro.dto.PapelDTO;
import br.com.hostelpro.entity.Papel;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PermissaoMapper.class })
public interface PapelMapper {

    PapelMapper INSTANCE = Mappers.getMapper(PapelMapper.class);

    // Mapeia permissões sem incluir usuários
    PapelDTO toDTO(Papel entity);

    @Mapping(target = "usuarios", ignore = true)
    Papel toEntity(PapelDTO dto);

    List<PapelDTO> toDTOList(List<Papel> entities);
    List<Papel> toEntityList(List<PapelDTO> dtos);
}