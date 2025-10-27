package br.com.hostelpro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.Quarto;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.QuartoRepository;

@Service
public class QuartoService {

	@Autowired
    private QuartoRepository repository;
    
	@Autowired
	private EstabelecimentoRepository estRepository;
    
	private Logger logger = LoggerFactory.getLogger(QuartoService.class);

    public Quarto criar(Quarto q) {
        Estabelecimento est = estRepository.findById(q.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + q.getEstabelecimento().getId()));
        q.setEstabelecimento(est);
        Quarto salvo = repository.save(q);
        logger.info("Quarto criado id={}", salvo.getId());
        return salvo;
    }

    public Quarto buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Quarto não encontrado: " + id));
    }

    public List<Quarto> listarPorEstabelecimento(Integer estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId);
    }

    public Quarto atualizar(Integer id, Quarto dados) {
        Quarto existente = buscarPorId(id);
        existente.setNumero(dados.getNumero());
        existente.setStatus(dados.getStatus());
        existente.setObservacao(dados.getObservacao());
        Quarto salvo = repository.save(existente);
        logger.info("Quarto atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) throw new NotFoundException("Quarto não encontrado: " + id);
        repository.deleteById(id);
        logger.info("Quarto deletado id={}", id);
    }
}
