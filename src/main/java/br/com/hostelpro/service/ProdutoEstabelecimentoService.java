package br.com.hostelpro.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hostelpro.dto.ProdutoEstabelecimentoDTO;
import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.mapper.ProdutoEstabelecimentoMapper;
import br.com.hostelpro.repository.ProdutoEstabelecimentoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoEstabelecimentoService {

    private final ProdutoEstabelecimentoRepository repository;
    private final ProdutoEstabelecimentoMapper mapper;

    public ProdutoEstabelecimentoService(ProdutoEstabelecimentoRepository repository,
                                         ProdutoEstabelecimentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProdutoEstabelecimentoDTO> listarPorEstabelecimento(Integer estabelecimentoId) {
        return repository.findByEstabelecimentoId(estabelecimentoId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoEstabelecimentoDTO salvar(ProdutoEstabelecimentoDTO dto) {
        ProdutoEstabelecimento entity = mapper.toEntity(dto);
        ProdutoEstabelecimento saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    public ProdutoEstabelecimentoDTO atualizar(Integer id, ProdutoEstabelecimentoDTO dto) {
        ProdutoEstabelecimento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        // Atualiza campos
        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setImagem(dto.getImagem());
        entity.setEstoque(dto.getEstoque());
        entity.setQuantidadeVendida(dto.getQuantidadeVendida());
        entity.setSituacao(dto.getSituacao());
        entity.setOrigemCadastro(dto.getOrigemCadastro());
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    public void deletar(Integer id) {
        repository.deleteById(id);
    }
}