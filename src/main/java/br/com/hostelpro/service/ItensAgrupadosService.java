package br.com.hostelpro.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hostelpro.entity.ItensAgrupados;
import br.com.hostelpro.repository.ItensAgrupadosRepository;

@Service
public class ItensAgrupadosService {

    private final ItensAgrupadosRepository repository;

    public ItensAgrupadosService(ItensAgrupadosRepository repository) {
        this.repository = repository;
    }

    public List<ItensAgrupados> listarPorEstabelecimento(Integer estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId);
    }

    @Transactional
    public ItensAgrupados salvar(ItensAgrupados entity) {
        return repository.save(entity);
    }

    @Transactional
    public ItensAgrupados atualizar(Integer id, ItensAgrupados entityAtualizado) {
        ItensAgrupados existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        existente.setNome(entityAtualizado.getNome());
        existente.setEstabelecimento(entityAtualizado.getEstabelecimento());
        existente.setTipoEstabelecimento(entityAtualizado.getTipoEstabelecimento());
        return repository.save(existente);
    }

    @Transactional
    public void deletar(Integer id) {
        repository.deleteById(id);
    }

    public ItensAgrupados buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }
}
