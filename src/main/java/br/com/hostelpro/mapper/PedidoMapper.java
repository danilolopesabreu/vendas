package br.com.hostelpro.mapper;

import br.com.hostelpro.dto.PedidoDTO;
import br.com.hostelpro.entity.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemPedidoMapper.class})
public interface PedidoMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(source = "quartoId", target = "quarto.id")
    @Mapping(source = "usuarioId", target = "usuario.id")
    Pedido toEntity(PedidoDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "quarto.id", target = "quartoId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    PedidoDTO toDTO(Pedido entity);
}
