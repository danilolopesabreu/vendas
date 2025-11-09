package br.com.hostelpro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.hostelpro.dto.PermissaoDTO;
import br.com.hostelpro.entity.Permissao;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissaoMapper {

    PermissaoMapper INSTANCE = Mappers.getMapper(PermissaoMapper.class);

    PermissaoDTO toDTO(Permissao entity);
    Permissao toEntity(PermissaoDTO dto);

    List<PermissaoDTO> toDTOList(List<Permissao> entities);
    List<Permissao> toEntityList(List<PermissaoDTO> dtos);
}