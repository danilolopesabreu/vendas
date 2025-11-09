package br.com.hostelpro.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.hostelpro.dto.ProdutoEstabelecimentoDTO;
import br.com.hostelpro.entity.ProdutoEstabelecimento;

@Mapper(componentModel = "spring")
public interface ProdutoEstabelecimentoMapper {

    ProdutoEstabelecimentoMapper INSTANCE = Mappers.getMapper(ProdutoEstabelecimentoMapper.class);

    @Mapping(source = "produtoBase.id", target = "produtoBaseId")
    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProdutoEstabelecimentoDTO toDTO(ProdutoEstabelecimento entity);

    @Mapping(source = "produtoBaseId", target = "produtoBase.id")
    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(source = "categoriaId", target = "categoria.id")
    ProdutoEstabelecimento toEntity(ProdutoEstabelecimentoDTO dto);
}