package br.com.hostelpro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.Produto;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.CategoriaProdutoRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
    private ProdutoRepository repository;
    
	@Autowired
	private EstabelecimentoRepository estRepository;
    
	@Autowired
	private CategoriaProdutoRepository categoriaRepository;
    
	private Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public Produto criar(Produto p) {
        Estabelecimento est = estRepository.findById(p.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + p.getEstabelecimento().getId()));
        p.setEstabelecimento(est);

        if (p.getCategoriaProduto() != null && p.getCategoriaProduto().getId() != null) {
            CategoriaProduto cat = categoriaRepository.findById(p.getCategoriaProduto().getId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + p.getCategoriaProduto().getId()));
            p.setCategoriaProduto(cat);
        }

        Produto salvo = repository.save(p);
        logger.info("Produto criado id={}", salvo.getId());
        return salvo;
    }

    public Produto buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Produto não encontrado: " + id));
    }

    public List<Produto> listarPorEstabelecimento(Integer estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId);
    }

    public List<Produto> listarPorEstabelecimentoOrdenadoPorQtdVendaENome(Integer estabelecimentoId) {
    	return repository.findByEstabelecimentoIdOrderByQuantidadeVendidaDescNomeAsc(estabelecimentoId);
    }

    public Produto atualizar(Integer id, Produto dados) {
        Produto existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setPreco(dados.getPreco());
        existente.setEstoque(dados.getEstoque());
        
        if (dados.getCategoriaProduto() != null && dados.getCategoriaProduto().getId() != null) {
            CategoriaProduto cat = categoriaRepository.findById(dados.getCategoriaProduto().getId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + dados.getCategoriaProduto().getId()));
            existente.setCategoriaProduto(cat);
        } else {
            existente.setCategoriaProduto(null);
        }
        
        Produto salvo = repository.save(existente);
        logger.info("Produto atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) throw new NotFoundException("Produto não encontrado: " + id);
        repository.deleteById(id);
        logger.info("Produto deletado id={}", id);
    }
}
