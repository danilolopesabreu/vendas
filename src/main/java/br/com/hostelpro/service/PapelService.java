package br.com.hostelpro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hostelpro.entity.Papel;
import br.com.hostelpro.repository.PapelRepository;

@Service
public class PapelService {

    private final PapelRepository papelRepository;

    public PapelService(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    @Transactional(readOnly = true)
    public List<Papel> listarTodos() {
        return papelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Papel> buscarPorId(Integer id) {
        return papelRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Papel> buscarPorNome(String nome) {
        return papelRepository.findByNome(nome);
    }

    @Transactional
    public Papel salvar(Papel papel) {
        // garante cascata nas permissões e usuários, se necessário
        return papelRepository.save(papel);
    }

    @Transactional
    public void deletar(Integer id) {
        if (papelRepository.existsById(id)) {
            papelRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Papel com ID " + id + " não encontrado.");
        }
    }
}