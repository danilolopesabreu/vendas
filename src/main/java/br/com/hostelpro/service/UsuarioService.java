package br.com.hostelpro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.Usuario;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository repository;
    
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
    
	private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public Usuario criar(Usuario u) {
        // valida estabelecimento
        Estabelecimento est = estabelecimentoRepository.findById(u.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + u.getEstabelecimento().getId()));
        u.setEstabelecimento(est);
        Usuario salvo = repository.save(u);
        logger.info("Usuário criado id={}", salvo.getId());
        return salvo;
    }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado: " + id));
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario atualizar(Integer id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setEmail(dados.getEmail());
        
        existente.setAtivo(dados.getAtivo());
        // não atualiza senha aqui (método separado poderia fazer isso)
        Usuario salvo = repository.save(existente);
        logger.info("Usuário atualizado id={}", salvo.getId());
        return salvo;
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) throw new NotFoundException("Usuário não encontrado: " + id);
        repository.deleteById(id);
        logger.info("Usuário deletado id={}", id);
    }
}
