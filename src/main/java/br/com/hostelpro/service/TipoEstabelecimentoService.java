package br.com.hostelpro.service;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hostelpro.entity.TipoEstabelecimento;
import br.com.hostelpro.repository.TipoEstabelecimentoRepository;

@Service
public class TipoEstabelecimentoService {

    private final TipoEstabelecimentoRepository tipoEstabelecimentoRepository;

    public TipoEstabelecimentoService(TipoEstabelecimentoRepository tipoEstabelecimentoRepository) {
        this.tipoEstabelecimentoRepository = tipoEstabelecimentoRepository;
    }

    @Transactional(readOnly = true)
    public List<TipoEstabelecimento> listarTodos() {
        return tipoEstabelecimentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<TipoEstabelecimento> buscarPorId(Integer id) {
        return tipoEstabelecimentoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<TipoEstabelecimento> buscarPorNome(String nome) {
        return tipoEstabelecimentoRepository.findByNome(nome);
    }

    @Transactional(readOnly = true)
    public Optional<TipoEstabelecimento> buscarPorAgrupador(String agrupador) {
        return tipoEstabelecimentoRepository.findByAgrupador(agrupador);
    }

    @Transactional
    public TipoEstabelecimento salvar(TipoEstabelecimento tipoEstabelecimento) {
        // CascadeType.ALL garante persistência automática dos estabelecimentos associados
        return tipoEstabelecimentoRepository.save(tipoEstabelecimento);
    }

    @Transactional
    public void deletar(Integer id) {
        if (tipoEstabelecimentoRepository.existsById(id)) {
            tipoEstabelecimentoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tipo de estabelecimento com ID " + id + " não encontrado.");
        }
    }
}