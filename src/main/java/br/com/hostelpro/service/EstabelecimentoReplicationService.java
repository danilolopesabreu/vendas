package br.com.hostelpro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.Papel;
import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.repository.CategoriaProdutoRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ProdutoEstabelecimentoRepository;
import br.com.hostelpro.repository.TipoEstabelecimentoRepository;
import jakarta.transaction.Transactional;

@Service
public class EstabelecimentoReplicationService {

	@Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	private ProdutoEstabelecimentoRepository produtoEstabelecimentoRepository;
	
	@Autowired
	private TipoEstabelecimentoRepository tipoEstabelecimentoRepository;
	
	@Autowired
	private RelevanciaService relevanciaService;
	
	@Autowired
	private PapelService papelService;

    /**
     * Cria um novo estabelecimento e replica as categorias selecionadas
     * do estabelecimento modelo.
     */
	@Transactional
	public Estabelecimento criarComCategoriasSelecionadas(Estabelecimento novoEstabelecimento) {

	    Integer idEstabelecimentoModelo = Estabelecimento.ID_MODELO;

	    // IDs das categorias selecionadas pelo usuário (somente raízes)
	    List<Integer> idsCategoriasSelecionadas = novoEstabelecimento.getCategoriaProduto().stream()
	            .filter(c -> c.getCategoriaPai() == null)
	            .map(CategoriaProduto::getId)
	            .collect(Collectors.toList());

	    novoEstabelecimento.setCategoriaProduto(null);

	    var papel = this.papelService.buscarPorId(Papel.ID_MODELO).get();
	    var usuario = novoEstabelecimento.getUsuarios().get(0);

	    usuario.setPapel(papel);
	    usuario.setEstabelecimento(novoEstabelecimento);

	    var tipoEstabelecimento = this.tipoEstabelecimentoRepository.findById(novoEstabelecimento.getTipoEstabelecimento().getId()).get();
	    novoEstabelecimento.setTipoEstabelecimento(tipoEstabelecimento);

	    // 1️⃣ Persiste o novo estabelecimento
	    Estabelecimento novoPersistido = estabelecimentoRepository.save(novoEstabelecimento);

	    // 2️⃣ Carrega o modelo
	    Estabelecimento modelo = estabelecimentoRepository.findById(idEstabelecimentoModelo)
	            .orElseThrow(() -> new RuntimeException("Estabelecimento modelo não encontrado"));

	    // 3️⃣ Filtra categorias selecionadas e raízes
	    List<CategoriaProduto> categoriasSelecionadas = modelo.getCategoriaProduto().stream()
	            .filter(c -> c.getCategoriaPai() == null)
	            .filter(c -> idsCategoriasSelecionadas.contains(c.getId()))
	            .collect(Collectors.toList());

	    if (categoriasSelecionadas.isEmpty()) {
	        throw new RuntimeException("Nenhuma categoria encontrada para os IDs informados.");
	    }

	    // 4️⃣ Listas para batch insert
	    List<CategoriaProduto> categoriasParaSalvar = new ArrayList<>();
	    List<ProdutoEstabelecimento> produtosEstabelecimentoParaSalvar = new ArrayList<>();

	    // 5️⃣ Clonar categorias recursivamente criando ProdutoEstabelecimento
	    for (CategoriaProduto c : categoriasSelecionadas) {
	        clonarCategoriaBatchParaProdutoEstabelecimento(c, novoPersistido, null, categoriasParaSalvar, produtosEstabelecimentoParaSalvar);
	    }

	    // 6️⃣ Persistir tudo em batch
	    categoriaProdutoRepository.saveAll(categoriasParaSalvar);
	    relevanciaService.calcularRelevanciaParaLista(produtosEstabelecimentoParaSalvar);
	    produtoEstabelecimentoRepository.saveAll(produtosEstabelecimentoParaSalvar);

	    return novoPersistido;
	}

	private CategoriaProduto clonarCategoriaBatchParaProdutoEstabelecimento(
	        CategoriaProduto original,
	        Estabelecimento novoEstabelecimento,
	        CategoriaProduto novaCategoriaPai,
	        List<CategoriaProduto> categoriasParaSalvar,
	        List<ProdutoEstabelecimento> produtosEstabelecimentoParaSalvar) {

	    CategoriaProduto copia = new CategoriaProduto();
	    copia.setNome(original.getNome());
	    copia.setImagem(original.getImagem());
	    copia.setEstabelecimento(novoEstabelecimento);
	    copia.setCategoriaPai(novaCategoriaPai);
	    copia.setCriadoEm(LocalDateTime.now());
	    copia.setAtualizadoEm(LocalDateTime.now());

	    categoriasParaSalvar.add(copia);

	    // Nó folha: criar ProdutoEstabelecimento com base nos produtos atuais
	    if (original.getSubcategorias() == null || original.getSubcategorias().isEmpty()) {
	        List<ProdutoEstabelecimento> novosProdutosEstabelecimento = original.getProdutos().stream().map(produtoOriginal -> {
	            ProdutoEstabelecimento pe = new ProdutoEstabelecimento();
	            pe.setProdutoBase(produtoOriginal); // referência ao produto original
	            pe.setEstabelecimento(novoEstabelecimento);
	            pe.setCategoria(copia);
	            pe.setNome(produtoOriginal.getNome());
	            pe.setDescricao(produtoOriginal.getDescricao());
	            pe.setPreco(produtoOriginal.getPreco());
	            pe.setImagem(produtoOriginal.getImagem());
	            pe.setEstoque(produtoOriginal.getEstoque());
	            pe.setQuantidadeVendida(produtoOriginal.getQuantidadeVendida());
	            pe.setSituacao(produtoOriginal.getSituacao());
	            pe.setOrigemCadastro(produtoOriginal.getOrigemCadastro());
	            pe.setCriadoEm(LocalDateTime.now());
	            pe.setAtualizadoEm(LocalDateTime.now());
	            return pe;
	        }).collect(Collectors.toList());

	        produtosEstabelecimentoParaSalvar.addAll(novosProdutosEstabelecimento);
	    }

	    // Clonar subcategorias recursivamente
	    if (original.getSubcategorias() != null && !original.getSubcategorias().isEmpty()) {
	        for (CategoriaProduto sub : original.getSubcategorias()) {
	            clonarCategoriaBatchParaProdutoEstabelecimento(sub, novoEstabelecimento, copia, categoriasParaSalvar, produtosEstabelecimentoParaSalvar);
	        }
	    }

	    return copia;
	}


}
