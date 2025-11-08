package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.hostelpro.dto.CategoriaProdutoDTO;
import br.com.hostelpro.entity.CategoriaProduto;

@Mapper(componentModel = "spring")
public interface CategoriaProdutoMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(target = "categoriaPai", ignore = true)
    CategoriaProduto toEntity(CategoriaProdutoDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(target = "subcategorias", qualifiedByName = "mapSubcategorias")
    CategoriaProdutoDTO toDTO(CategoriaProduto entity);

    List<CategoriaProdutoDTO> toResponseDTOList(List<CategoriaProduto> entities);

    // Evita loop: subcategorias não terão "categoriaPai"
    @Named("mapSubcategorias")
    @IterableMapping(qualifiedByName = "semCategoriaPai")
    List<CategoriaProdutoDTO> mapSubcategorias(List<CategoriaProduto> entities);

    @Named("semCategoriaPai")
    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(target = "categoriaPai", ignore = true)
    @Mapping(target = "subcategorias", ignore = true) // corta a recursão
    CategoriaProdutoDTO toDTOWithoutParent(CategoriaProduto entity);
}
