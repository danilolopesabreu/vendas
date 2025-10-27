package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.ClienteDTO;
import br.com.hostelpro.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    Cliente toEntity(ClienteDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    ClienteDTO toDTO(Cliente entity);
}
