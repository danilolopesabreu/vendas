package br.com.hostelpro.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hostelpro.dto.ItensAgrupadosDTO;
import br.com.hostelpro.entity.ItensAgrupados;

@Mapper(componentModel = "spring")
public interface ItensAgrupadosMapper {

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "tipoEstabelecimento.id", target = "tipoEstabelecimentoId")
    ItensAgrupadosDTO toDTO(ItensAgrupados entity);

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(source = "tipoEstabelecimentoId", target = "tipoEstabelecimento.id")
    ItensAgrupados toEntity(ItensAgrupadosDTO dto);
}
