package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.hostelpro.dto.TipoEstabelecimentoDTO;
import br.com.hostelpro.entity.TipoEstabelecimento;

@Mapper(componentModel = "spring", uses = { EstabelecimentoMapper.class, AgrupadorMapper.class})
public interface TipoEstabelecimentoMapper {

	TipoEstabelecimentoMapper INSTANCE = Mappers.getMapper(TipoEstabelecimentoMapper.class);

    @Mapping(target = "agrupador.tipoEstabelecimento", ignore = true)
	TipoEstabelecimentoDTO toDTO(TipoEstabelecimento entity);

    @Mapping(target = "agrupador.tipoEstabelecimento", ignore = true)
	TipoEstabelecimento toEntity(TipoEstabelecimentoDTO dto);

	// Mapeamento de listas
	List<TipoEstabelecimentoDTO> toDTOList(List<TipoEstabelecimento> entities);

	List<TipoEstabelecimento> toEntityList(List<TipoEstabelecimentoDTO> dtos);
}