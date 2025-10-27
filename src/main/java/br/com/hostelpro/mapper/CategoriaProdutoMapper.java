package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.CategoriaProdutoDTO;
import br.com.hostelpro.entity.CategoriaProduto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaProdutoMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(source = "categoriaPaiId", target = "categoriaPai.id")
    CategoriaProduto toEntity(CategoriaProdutoDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "categoriaPai.id", target = "categoriaPaiId")
    CategoriaProdutoDTO toDTO(CategoriaProduto entity);
}
