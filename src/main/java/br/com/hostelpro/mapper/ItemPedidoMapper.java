package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import br.com.hostelpro.dto.ItemPedidoDTO;
import br.com.hostelpro.entity.ItemPedido;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {

    @Mapping(source = "produtoId", target = "produto.id")
    @Mapping(source = "clienteId", target = "cliente.id")
    ItemPedido toEntity(ItemPedidoDTO dto);

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(target = "produto", source = "produto")
    ItemPedidoDTO toDTO(ItemPedido entity);
    
    List<ItemPedidoDTO> toDTOList(List<ItemPedido> entities);
    
    @AfterMapping
    default void ajustarCliente(@MappingTarget ItemPedido entity, ItemPedidoDTO dto) {
        if (dto.getClienteId() == null) {
            entity.setCliente(null);
        }
    }
}
