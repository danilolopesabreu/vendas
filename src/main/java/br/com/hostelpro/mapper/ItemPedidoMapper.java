package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hostelpro.dto.ItemPedidoDTO;
import br.com.hostelpro.entity.ItemPedido;

@Mapper(componentModel = "spring", uses = {ProdutoMapper.class})
public interface ItemPedidoMapper {

    @Mapping(source = "produtoId", target = "produto.id")
    ItemPedido toEntity(ItemPedidoDTO dto);

    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(target = "produto", source = "produto")
    ItemPedidoDTO toDTO(ItemPedido entity);
    
    List<ItemPedidoDTO> toDTOList(List<ItemPedido> entities);
    
}
