package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.ItemPedidoDTO;
import br.com.hostelpro.entity.ItemPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    @Mapping(source = "produtoId", target = "produto.id")
    @Mapping(source = "clienteId", target = "cliente.id")
    ItemPedido toEntity(ItemPedidoDTO dto);

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "cliente.id", target = "clienteId")
    ItemPedidoDTO toDTO(ItemPedido entity);
}
