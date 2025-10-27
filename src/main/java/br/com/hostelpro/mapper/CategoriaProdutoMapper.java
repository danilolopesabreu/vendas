package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.hostelpro.dto.CategoriaProdutoDTO;
import br.com.hostelpro.entity.CategoriaProduto;

@Mapper(componentModel = "spring")
public interface CategoriaProdutoMapper {

	@Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
	@Mapping(target = "categoriaPai", ignore = true)
	CategoriaProduto toEntity(CategoriaProdutoDTO dto);

	@Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
	@Mapping(source = "categoriaPai.id", target = "categoriaPaiId")
	@Mapping(target = "subcategorias", source = "subcategorias")
	CategoriaProdutoDTO toDTO(CategoriaProduto entity);

	List<CategoriaProdutoDTO> toResponseDTOList(List<CategoriaProduto> entities);
	
	@AfterMapping
	default void setCategoriaPai(CategoriaProdutoDTO dto, @MappingTarget CategoriaProduto entity) {
		if (dto.getCategoriaPaiId() != null) {
			entity.setCategoriaPai(new CategoriaProduto(dto.getCategoriaPaiId()));
		}
	}
}
