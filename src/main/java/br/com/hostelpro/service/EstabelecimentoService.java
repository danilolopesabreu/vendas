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
    private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	private EstabelecimentoReplicationService estabelecimentoReplicationService;
	    
	private Logger logger = LoggerFactory.getLogger(EstabelecimentoService.class);

    public Estabelecimento criar(Estabelecimento e) {
//        Estabelecimento salvo = estabelecimentoRepository.save(e);
    	
    	var salvo = estabelecimentoReplicationService.criarComCategoriasSelecionadas(e);
    	
        logger.info("Estabelecimento criado id={}", salvo.getId());
        
//        Usuario u = e.getUsuarios().get(0);
//        u.setEstabelecimento(salvo);
//        this.usuarioService.criar(u);
        
        return salvo;
    }

    public Estabelecimento buscarPorId(Integer id) {
        return estabelecimentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + id));
    }

    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }

    public Estabelecimento atualizar(Integer id, Estabelecimento dados) {
        Estabelecimento existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setCnpj(dados.getCnpj());
        existente.setEmail(dados.getEmail());
        existente.setTelefone(dados.getTelefone());
        existente.setEndereco(dados.getEndereco());
        Estabelecimento salvo = estabelecimentoRepository.save(existente);
        logger.info("Estabelecimento atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!estabelecimentoRepository.existsById(id)) {
            throw new NotFoundException("Estabelecimento não encontrado: " + id);
        }
        estabelecimentoRepository.deleteById(id);
        logger.info("Estabelecimento deletado id={}", id);
    }
}
