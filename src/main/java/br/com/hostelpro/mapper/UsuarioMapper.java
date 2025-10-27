package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.UsuarioDTO;
import br.com.hostelpro.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    Usuario toEntity(UsuarioDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    UsuarioDTO toDTO(Usuario entity);
}
