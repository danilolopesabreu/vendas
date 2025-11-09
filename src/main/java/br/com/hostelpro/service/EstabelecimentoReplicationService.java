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
import br.com.hostelpro.entity.Produto;
import br.com.hostelpro.entity.TipoEstabelecimento;
import br.com.hostelpro.repository.CategoriaProdutoRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ProdutoRepository;
import br.com.hostelpro.repository.TipoEstabelecimentoRepository;
import jakarta.transaction.Transactional;

@Service
public class EstabelecimentoReplicationService {

	@Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@Autowired
    private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private TipoEstabelecimentoRepository tipoEstabelecimentoRepository;
	
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
        List<Produto> produtosParaSalvar = new ArrayList<>();

        // 5️⃣ Clonar categorias recursivamente
        for (CategoriaProduto c : categoriasSelecionadas) {
            clonarCategoriaBatch(c, novoPersistido, null, categoriasParaSalvar, produtosParaSalvar);
        }

        // 6️⃣ Persistir tudo em batch
        categoriaProdutoRepository.saveAll(categoriasParaSalvar);
        produtoRepository.saveAll(produtosParaSalvar);

        return novoPersistido;
    }

    private CategoriaProduto clonarCategoriaBatch(
            CategoriaProduto original,
            Estabelecimento novoEstabelecimento,
            CategoriaProduto novaCategoriaPai,
            List<CategoriaProduto> categoriasParaSalvar,
            List<Produto> produtosParaSalvar) {

        CategoriaProduto copia = new CategoriaProduto();
        copia.setNome(original.getNome());
        copia.setImagem(original.getImagem());
        copia.setEstabelecimento(novoEstabelecimento);
        copia.setCategoriaPai(novaCategoriaPai);
        copia.setCriadoEm(LocalDateTime.now());
        copia.setAtualizadoEm(LocalDateTime.now());

        categoriasParaSalvar.add(copia);

        // Nó folha: clonar produtos
        if (original.getSubcategorias() == null || original.getSubcategorias().isEmpty()) {
            List<Produto> novosProdutos = original.getProdutos().stream().map(produtoOriginal -> {
                Produto novoProduto = new Produto();
                novoProduto.setNome(produtoOriginal.getNome());
                novoProduto.setPreco(produtoOriginal.getPreco());
                novoProduto.setDescricao(produtoOriginal.getDescricao());
                novoProduto.setImagem(produtoOriginal.getImagem());
                novoProduto.setEstabelecimento(novoEstabelecimento);
                novoProduto.setCategoriaProduto(copia);
                novoProduto.setOrigemCadastro(produtoOriginal.getOrigemCadastro());
                novoProduto.setSituacao(produtoOriginal.getSituacao());
                novoProduto.setQuantidadeVendida(produtoOriginal.getQuantidadeVendida());
                return novoProduto;
            }).collect(Collectors.toList());

            produtosParaSalvar.addAll(novosProdutos);
        }

        // Clonar subcategorias recursivamente
        if (original.getSubcategorias() != null && !original.getSubcategorias().isEmpty()) {
            for (CategoriaProduto sub : original.getSubcategorias()) {
                clonarCategoriaBatch(sub, novoEstabelecimento, copia, categoriasParaSalvar, produtosParaSalvar);
            }
        }

        return copia;
    }


}
