package br.com.hostelpro.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.ItemPedido;
import br.com.hostelpro.entity.ItensAgrupados;
import br.com.hostelpro.entity.Pedido;
import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.entity.Usuario;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ItemPedidoRepository;
import br.com.hostelpro.repository.PedidoRepository;
import br.com.hostelpro.repository.ProdutoEstabelecimentoRepository;
import br.com.hostelpro.repository.UsuarioRepository;

@Service
public class PedidoService {

	@Autowired
    private PedidoRepository pedidoRepository;
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ItensAgrupadosService itensAgrupadosService;
	
	@Autowired
	private ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository; 
    
	private Logger logger = LoggerFactory.getLogger(PedidoService.class);

    public Pedido criar(Pedido pedido) {
        // valida estabelecimento
        Estabelecimento est = estabelecimentoRepository.findById(pedido.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + pedido.getEstabelecimento().getId()));
        pedido.setEstabelecimento(est);

        // verifica quarto
//        Agrupador quarto = quartoRepository.findById(pedido.getQuarto().getId())
//                .orElseThrow(() -> new NotFoundException("Quarto não encontrado: " + pedido.getQuarto().getId()));
//        pedido.setQuarto(quarto);

        // verifica usuario
        Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + pedido.getUsuario().getId()));
        pedido.setUsuario(usuario);

        if(!pedido.getEstabelecimento().getTipoEstabelecimento().getAgrupador().getNome().equals("pedido")) {
        	ItensAgrupados itensAgrupados = itensAgrupadosService.findById(pedido.getItensAgrupados().getId());
        	pedido.setItensAgrupados(itensAgrupados);
        }

        // Para cada item: validar produto e calcular total
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
            	
            	item.setStatus("aberto");
            	
            	ProdutoEstabelecimento produtoEstabelecimento = produtoEstabelecimentoRepository.findById(item.getProdutoEstabelecimento().getId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + item.getProdutoEstabelecimento().getId()));
                
            	produtoEstabelecimento.setQuantidadeVendida(produtoEstabelecimento.getQuantidadeVendida() + item.getQuantidade());
            	
                item.setProdutoEstabelecimento(produtoEstabelecimento);
                
            }
        }
        
        pedido.setStatus("aberto");
        Pedido salvo = pedidoRepository.save(pedido); // cascades items
        
        logger.info("Pedido criado id={}", salvo.getId());
        
        return salvo;
    }

    public Pedido buscarPorId(Integer id) {
        return pedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado: " + id));
    }

    public List<Pedido> listarPorEstabelecimento(Integer estabelecimentoId) {
        return pedidoRepository.findByEstabelecimentoId(estabelecimentoId);
    }

    public List<Pedido> listarPorEstabelecimentoOrderDataCriacaoStatusOrdenado(Integer estabelecimentoId) {
    	return pedidoRepository.listarPorEstabelecimentoOrderDataCriacaoStatusOrdenado(estabelecimentoId);
    }
    
//    public List<Pedido> listarPorEstabelecimentoEQuarto(Integer estabelecimentoId, String numeroQuarto) {
//        List<Pedido> pedidos = pedidoRepository.findByEstabelecimentoAndOptionalQuartoWithProdutos(estabelecimentoId, numeroQuarto);
//        return pedidos;
//    }

    public Pedido atualizar(Pedido dados) {
    	
    	Pedido pedido = buscarPorId(dados.getId());
    	pedido.setStatus(dados.getStatus());
    	
    	Map<Integer, ItemPedido> itensMap = pedido.getItens().stream()
    	        .collect(Collectors.toMap(ItemPedido::getId, i -> i));
    	
    	for (ItemPedido umItem : dados.getItens()) {
    	    if (umItem.getId() != null && itensMap.containsKey(umItem.getId())) {
    	        // Atualizar item existente
    	        ItemPedido item = itensMap.get(umItem.getId());
    	        item.setQuantidade(umItem.getQuantidade());
    	        item.setPrecoUnitario(umItem.getPrecoUnitario());
    	        item.setPrecoTotal(umItem.getPrecoTotal());
    	        item.setStatus(umItem.getStatus());
    	    } else {
    	        // Novo item
    	        ItemPedido novoItem = new ItemPedido();
    	        novoItem.setPedido(pedido);
    	        novoItem.setProdutoEstabelecimento(umItem.getProdutoEstabelecimento());
    	        novoItem.setQuantidade(umItem.getQuantidade());
    	        novoItem.setPrecoUnitario(umItem.getPrecoUnitario());
    	        novoItem.setPrecoTotal(umItem.getPrecoTotal());
    	        novoItem.setStatus(umItem.getStatus());
    	        pedido.getItens().add(novoItem);
    	    }
    	}
    	
    	Pedido salvo = pedidoRepository.save(pedido);
    	
    	logger.info("Pedido atualizado id={}", salvo.getId());
    	
    	return salvo;
    }
    
    public Pedido atualizar(Integer id, Pedido dados) {
        Pedido existente = buscarPorId(id);
        existente.setStatus(dados.getStatus());
        Pedido salvo = pedidoRepository.save(existente);
        logger.info("Pedido atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!pedidoRepository.existsById(id)) throw new NotFoundException("Pedido não encontrado: " + id);
        pedidoRepository.deleteById(id);
        logger.info("Pedido deletado id={}", id);
    }
    
    public void cancelarItemPedido(Integer id) {
    	var itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new NotFoundException("ItemPedido não encontrado: " + id));
    	itemPedido.setStatus("cancelado");
    	itemPedidoRepository.save(itemPedido);
    	logger.info("ItemPedido cancelado id={}", id);
    }
    
    public void deletarItemPedido(Integer id) {
    	if (!itemPedidoRepository.existsById(id)) throw new NotFoundException("ItemPedido não encontrado: " + id);
    	itemPedidoRepository.deleteById(id);
    	logger.info("ItemPedido deletado id={}", id);
    }
}
