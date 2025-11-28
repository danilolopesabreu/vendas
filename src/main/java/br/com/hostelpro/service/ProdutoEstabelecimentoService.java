package br.com.hostelpro.service;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.repository.ProdutoEstabelecimentoRepository;

@Service
public class ProdutoEstabelecimentoService {

	@Autowired
    private ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<ProdutoEstabelecimento> listarProdutosMaisVendidosPorEstabelecimento(Integer estabelecimentoId) {
        return produtoEstabelecimentoRepository.findByEstabelecimentoIdOrderByRelevanciaDescQuantidadeVendidaDescNomeAsc(estabelecimentoId);
    }

    public ProdutoEstabelecimento salvar(ProdutoEstabelecimento entity) {
        return produtoEstabelecimentoRepository.save(entity);
    }

    public ProdutoEstabelecimento atualizar(ProdutoEstabelecimento entityAtualizada) {
    	
        ProdutoEstabelecimento entity = produtoEstabelecimentoRepository.findById(entityAtualizada.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        try {
			String url = cloudinaryService.uploadImageBase64(entityAtualizada.getImagem(), entityAtualizada.getEstabelecimento().getId());
			entity.setImagem(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // Atualiza campos
        entity.setNome(entityAtualizada.getNome());
        entity.setDescricao(entityAtualizada.getDescricao());
        entity.setPreco(entityAtualizada.getPreco());
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
