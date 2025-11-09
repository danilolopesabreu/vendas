package br.com.hostelpro.mapper;
import java.util.List;

import org.mapstruct.Mapper;

import br.com.hostelpro.dto.AgrupadorDTO;
import br.com.hostelpro.entity.Agrupador;

@Mapper(componentModel = "spring")
public interface AgrupadorMapper {

    Agrupador toEntity(AgrupadorDTO dto);

    AgrupadorDTO toDTO(Agrupador entity);

    List<AgrupadorDTO> toDTOList(List<Agrupador> entities);
    List<Agrupador> toEntityList(List<AgrupadorDTO> dtos);
}