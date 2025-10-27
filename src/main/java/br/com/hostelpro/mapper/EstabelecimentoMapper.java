package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.EstabelecimentoDTO;
import br.com.hostelpro.entity.Estabelecimento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {
    Estabelecimento toEntity(EstabelecimentoDTO dto);
    EstabelecimentoDTO toDTO(Estabelecimento entity);
}
