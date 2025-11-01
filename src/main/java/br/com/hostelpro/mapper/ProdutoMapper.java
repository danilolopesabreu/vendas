package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.ProdutoDTO;
import br.com.hostelpro.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(source = "categoriaId", target = "categoriaProduto.id")
    Produto toEntity(ProdutoDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "categoriaProduto.id", target = "categoriaId")
    ProdutoDTO toDTO(Produto entity);
}
