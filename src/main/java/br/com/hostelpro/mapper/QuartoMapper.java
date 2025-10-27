package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.QuartoDTO;
import br.com.hostelpro.entity.Quarto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuartoMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    Quarto toEntity(QuartoDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    QuartoDTO toDTO(Quarto entity);
}
