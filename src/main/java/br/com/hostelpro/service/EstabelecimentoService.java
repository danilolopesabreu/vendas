package br.com.hostelpro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

	@Autowired
    private EstabelecimentoRepository repository;
    
	private Logger logger = LoggerFactory.getLogger(EstabelecimentoService.class);

    public Estabelecimento criar(Estabelecimento e) {
        Estabelecimento salvo = repository.save(e);
        logger.info("Estabelecimento criado id={}", salvo.getId());
        return salvo;
    }

    public Estabelecimento buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + id));
    }

    public List<Estabelecimento> listarTodos() {
        return repository.findAll();
    }

    public Estabelecimento atualizar(Integer id, Estabelecimento dados) {
        Estabelecimento existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setCnpj(dados.getCnpj());
        existente.setEmail(dados.getEmail());
        existente.setTelefone(dados.getTelefone());
        existente.setEndereco(dados.getEndereco());
        Estabelecimento salvo = repository.save(existente);
        logger.info("Estabelecimento atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Estabelecimento não encontrado: " + id);
        }
        repository.deleteById(id);
        logger.info("Estabelecimento deletado id={}", id);
    }
}
