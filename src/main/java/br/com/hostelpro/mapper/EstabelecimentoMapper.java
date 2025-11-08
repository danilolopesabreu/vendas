package br.com.hostelpro.mapper;

import org.mapstruct.Mapper;

import br.com.hostelpro.dto.EstabelecimentoDTO;
import br.com.hostelpro.entity.Estabelecimento;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, CategoriaProdutoMapper.class})
public interface EstabelecimentoMapper {
    Estabelecimento toEntity(EstabelecimentoDTO dto);
    EstabelecimentoDTO toDTO(Estabelecimento entity);
}
