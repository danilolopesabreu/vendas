package br.com.hostelpro.service;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.exception.NotFoundException;
import br.com.hostelpro.repository.CategoriaProdutoRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ProdutoEstabelecimentoRepository;

@Service
public class ProdutoEstabelecimentoService {

	@Autowired
    private ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
	@Autowired
	private EstabelecimentoRepository estRepository;
    
	@Autowired
	private CategoriaProdutoRepository categoriaRepository;

    public List<ProdutoEstabelecimento> listarProdutosMaisVendidosPorEstabelecimento(Integer estabelecimentoId) {
        return produtoEstabelecimentoRepository.findByEstabelecimentoIdOrderByRelevanciaDescQuantidadeVendidaDescNomeAsc(estabelecimentoId);
    }

    public ProdutoEstabelecimento criar(ProdutoEstabelecimento produtoEstabelecimento) {
    	
    	Estabelecimento est = estRepository.findById(produtoEstabelecimento.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado: " + produtoEstabelecimento.getEstabelecimento().getId()));
        produtoEstabelecimento.setEstabelecimento(est);

        if (produtoEstabelecimento.getCategoria() != null && produtoEstabelecimento.getCategoria().getId() != null) {
            CategoriaProduto cat = categoriaRepository.findById(produtoEstabelecimento.getCategoria().getId())
                    .orElseThrow(() -> new NotFoundException("Categoria não encontrada: " + produtoEstabelecimento.getCategoria().getId()));
            produtoEstabelecimento.setCategoria(cat);
        }
        
        produtoEstabelecimento.setProdutoBase(null);
        
        try {

    		String url = cloudinaryService.uploadImageBase64(produtoEstabelecimento.getImagem(), est.getId());
    		produtoEstabelecimento.setImagem(url);
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return produtoEstabelecimentoRepository.save(produtoEstabelecimento);
    }

    public ProdutoEstabelecimento atualizar(ProdutoEstabelecimento entityAtualizada) {
    	
        ProdutoEstabelecimento entity = produtoEstabelecimentoRepository.findById(entityAtualizada.getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        try {

        	if (entityAtualizada.getImagem() != null && entityAtualizada.getImagem().startsWith("data:image")) {
        		String url = cloudinaryService.uploadImageBase64(entityAtualizada.getImagem(), entityAtualizada.getEstabelecimento().getId());
        		entity.setImagem(url);
        	}
			
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
