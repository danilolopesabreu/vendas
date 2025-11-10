package br.com.hostelpro.service;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.repository.ProdutoEstabelecimentoRepository;

@Service
public class ProdutoEstabelecimentoService {

    private final ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;

    public ProdutoEstabelecimentoService(ProdutoEstabelecimentoRepository repository) {
        this.produtoEstabelecimentoRepository = repository;
    }

    public List<ProdutoEstabelecimento> listarPorEstabelecimento(Integer estabelecimentoId) {
        return produtoEstabelecimentoRepository.findByEstabelecimentoIdOrderByQuantidadeVendidaDescNomeAsc(estabelecimentoId);
    }

    public ProdutoEstabelecimento salvar(ProdutoEstabelecimento entity) {
        return produtoEstabelecimentoRepository.save(entity);
    }

    public ProdutoEstabelecimento atualizar(Integer id, ProdutoEstabelecimento entityAtualizada) {
        ProdutoEstabelecimento entity = produtoEstabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualiza campos
        entity.setNome(entityAtualizada.getNome());
        entity.setDescricao(entityAtualizada.getDescricao());
        entity.setPreco(entityAtualizada.getPreco());
        entity.setImagem(entityAtualizada.getImagem());
        entity.setEstoque(entityAtualizada.getEstoque());
        entity.setQuantidadeVendida(entityAtualizada.getQuantidadeVendida());
        entity.setSituacao(entityAtualizada.getSituacao());
        entity.setOrigemCadastro(entityAtualizada.getOrigemCadastro());

        return produtoEstabelecimentoRepository.save(entity);
    }

    public void deletar(Integer id) {
        produtoEstabelecimentoRepository.deleteById(id);
    }
}
