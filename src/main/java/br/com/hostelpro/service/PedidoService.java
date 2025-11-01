package br.com.hostelpro.service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.Cliente;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.ItemPedido;
import br.com.hostelpro.entity.Pedido;
import br.com.hostelpro.entity.Produto;
import br.com.hostelpro.entity.Quarto;
import br.com.hostelpro.entity.Usuario;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.ClienteRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ItemPedidoRepository;
import br.com.hostelpro.repository.PedidoRepository;
import br.com.hostelpro.repository.ProdutoRepository;
import br.com.hostelpro.repository.QuartoRepository;
import br.com.hostelpro.repository.UsuarioRepository;

@Service
public class PedidoService {

	@Autowired
    private PedidoRepository pedidoRepository;
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	@Autowired
	private QuartoRepository quartoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
    
	private Logger logger = LoggerFactory.getLogger(PedidoService.class);

    public Pedido criar(Pedido pedido) {
        // valida estabelecimento
        Estabelecimento est = estabelecimentoRepository.findById(pedido.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + pedido.getEstabelecimento().getId()));
        pedido.setEstabelecimento(est);

        // verifica quarto
        Quarto quarto = quartoRepository.findById(pedido.getQuarto().getId())
                .orElseThrow(() -> new NotFoundException("Quarto não encontrado: " + pedido.getQuarto().getId()));
        pedido.setQuarto(quarto);

        // verifica usuario
        Usuario usuario = usuarioRepository.findById(pedido.getUsuario().getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + pedido.getUsuario().getId()));
        pedido.setUsuario(usuario);

        // Para cada item: validar produto e calcular total
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
              
            	Produto produto = produtoRepository.findById(item.getProduto().getId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + item.getProduto().getId()));
                
            	produto.setQuantidadeVendida(produto.getQuantidadeVendida() + item.getQuantidade());
            	
                item.setProduto(produto);
                
            }
        }

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
    
    public List<Pedido> listarPorEstabelecimentoEQuarto(Integer estabelecimentoId, String numeroQuarto) {
        List<Pedido> pedidos = pedidoRepository.findByEstabelecimentoAndOptionalQuartoWithProdutos(estabelecimentoId, numeroQuarto);
        return pedidos;
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
}
