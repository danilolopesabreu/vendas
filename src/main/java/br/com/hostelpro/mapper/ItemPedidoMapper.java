package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.hostelpro.dto.ItemPedidoDTO;
import br.com.hostelpro.entity.ItemPedido;

@Mapper(componentModel = "spring", uses = {ProdutoEstabelecimentoMapper.class})
public interface ItemPedidoMapper {

    ItemPedido toEntity(ItemPedidoDTO dto);

    ItemPedidoDTO toDTO(ItemPedido entity);
    
    List<ItemPedidoDTO> toDTOList(List<ItemPedido> entities);
    
}
