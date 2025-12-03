package br.com.hostelpro.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.hostelpro.dto.PedidoDTO;
import br.com.hostelpro.entity.Pedido;

@Mapper(componentModel = "spring", uses = {ItemPedidoMapper.class, ItensAgrupadosMapper.class, TipoEstabelecimentoMapper.class})
public interface PedidoMapper {

    @Mapping(source = "estabelecimentoId", target = "estabelecimento.id")
    @Mapping(source = "usuarioId", target = "usuario.id")
    @Mapping(target = "dataCriacao", defaultExpression = "java(java.time.LocalDateTime.now())")
    Pedido toEntity(PedidoDTO dto);

    @Mapping(source = "estabelecimento.id", target = "estabelecimentoId")
    @Mapping(source = "estabelecimento.tipoEstabelecimento", target = "tipoEstabelecimento")
    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(target = "itens", source = "itens")
    PedidoDTO toDTO(Pedido entity);
    
    List<PedidoDTO> toDTOList(List<Pedido> entities);
}
