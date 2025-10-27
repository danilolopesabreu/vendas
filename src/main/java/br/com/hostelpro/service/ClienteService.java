package br.com.hostelpro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.Cliente;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.ClienteRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;

@Service
public class ClienteService {

	@Autowired
    private ClienteRepository repository;
    
	@Autowired
	private EstabelecimentoRepository estRepository;
    
	private Logger logger = LoggerFactory.getLogger(ClienteService.class);

    public Cliente criar(Cliente c) {
        Estabelecimento est = estRepository.findById(c.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + c.getEstabelecimento().getId()));
        c.setEstabelecimento(est);
        Cliente salvo = repository.save(c);
        logger.info("Cliente criado id={}", salvo.getId());
        return salvo;
    }

    public Cliente buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado: " + id));
    }

    public List<Cliente> listarPorEstabelecimento(Integer estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId);
    }

    public Cliente atualizar(Integer id, Cliente dados) {
        Cliente existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        existente.setTelefone(dados.getTelefone());
        Cliente salvo = repository.save(existente);
        logger.info("Cliente atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) throw new NotFoundException("Cliente não encontrado: " + id);
        repository.deleteById(id);
        logger.info("Cliente deletado id={}", id);
    }
}
