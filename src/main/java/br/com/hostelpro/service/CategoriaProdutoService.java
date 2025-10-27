package br.com.hostelpro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.CategoriaProdutoRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;

@Service
public class CategoriaProdutoService {

	@Autowired
    private CategoriaProdutoRepository repository;
    
    @Autowired
    private EstabelecimentoRepository estRepository;
    
    private Logger logger = LoggerFactory.getLogger(CategoriaProdutoService.class);

    public CategoriaProduto criar(CategoriaProduto c) {
        Estabelecimento est = estRepository.findById(c.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + c.getEstabelecimento().getId()));
        c.setEstabelecimento(est);

        if (c.getCategoriaPai() != null && c.getCategoriaPai().getId() != null) {
            CategoriaProduto pai = repository.findById(c.getCategoriaPai().getId())
                    .orElseThrow(() -> new NotFoundException("Categoria pai não encontrada: " + c.getCategoriaPai().getId()));
            c.setCategoriaPai(pai);
        }

        CategoriaProduto salvo = repository.save(c);
        logger.info("Categoria criada id={}", salvo.getId());
        return salvo;
    }

    public CategoriaProduto buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + id));
    }

    public List<CategoriaProduto> listarPorEstabelecimento(Integer estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId);
    }

    public CategoriaProduto atualizar(Integer id, CategoriaProduto dados) {
        CategoriaProduto existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        // categoria pai opcional
        if (dados.getCategoriaPai() != null && dados.getCategoriaPai().getId() != null) {
            CategoriaProduto pai = repository.findById(dados.getCategoriaPai().getId())
                    .orElseThrow(() -> new NotFoundException("Categoria pai não encontrada: " + dados.getCategoriaPai().getId()));
            existente.setCategoriaPai(pai);
        } else {
            existente.setCategoriaPai(null);
        }
        CategoriaProduto salvo = repository.save(existente);
        logger.info("Categoria atualizada id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) throw new NotFoundException("Categoria não encontrada: " + id);
        repository.deleteById(id);
        logger.info("Categoria deletada id={}", id);
    }
}
