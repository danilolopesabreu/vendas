package br.com.hostelpro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.entity.Produto;
import br.com.hostelpro.repository.CategoriaProdutoRepository;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;

@Service
public class EstabelecimentoReplicationService {

	@Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    /**
     * Cria um novo estabelecimento e replica as categorias selecionadas
     * do estabelecimento modelo.
     */
    @Transactional
    public Estabelecimento criarComCategoriasSelecionadas(Estabelecimento novoEstabelecimento) {

        final Integer ID_MODELO = Estabelecimento.ID_MODELO;

        // 1️⃣ Extrai apenas os IDs das categorias selecionadas vindas do front
        List<Integer> idsCategoriasSelecionadas = Optional.ofNullable(novoEstabelecimento.getCategoriaProduto())
                .orElse(Collections.emptyList())
                .stream()
                .map(CategoriaProduto::getId)
                .toList();

        // 2️⃣ Garante que o novo estabelecimento não tenha categorias transientes
        novoEstabelecimento.setCategoriaProduto(null);

        // 3️⃣ Salva o novo estabelecimento para torná-lo gerenciado
        novoEstabelecimento.getUsuarios().get(0).setEstabelecimento(novoEstabelecimento);
        Estabelecimento novoPersistido = estabelecimentoRepository.saveAndFlush(novoEstabelecimento);

        // 4️⃣ Carrega o modelo base
        Estabelecimento modelo = estabelecimentoRepository.findById(ID_MODELO)
                .orElseThrow(() -> new IllegalStateException("Estabelecimento modelo não encontrado."));

        // 5️⃣ Filtra as categorias selecionadas que pertencem ao modelo
        List<CategoriaProduto> categoriasSelecionadas = modelo.getCategoriaProduto().stream()
                .filter(c -> idsCategoriasSelecionadas.contains(c.getId()))
                .collect(Collectors.toList());

        if (categoriasSelecionadas.isEmpty()) {
            throw new IllegalStateException("Nenhuma categoria válida foi selecionada para clonagem.");
        }

        // 6️⃣ Clona recursivamente as categorias e subcategorias
        List<CategoriaProduto> categoriasClonadas = new ArrayList<>();
        for (CategoriaProduto categoriaOriginal : categoriasSelecionadas) {
            CategoriaProduto copia = clonarCategoria(categoriaOriginal, novoPersistido, null);
            categoriasClonadas.add(copia);
        }

        // 7️⃣ Salva as categorias clonadas (com cascade salva filhos e produtos)
        categoriaProdutoRepository.saveAll(categoriasClonadas);

        return novoPersistido;
    }

    /**
     * Clona uma categoria e toda a sua árvore de subcategorias e produtos.
     * Garante que todas as referências usem o Estabelecimento gerenciado.
     */
    private CategoriaProduto clonarCategoria(
            CategoriaProduto original,
            Estabelecimento estabelecimentoGerenciado,
            CategoriaProduto novaCategoriaPai) {

        CategoriaProduto copia = new CategoriaProduto();
        copia.setNome(original.getNome());
        copia.setImagem(original.getImagem());
        copia.setEstabelecimento(estabelecimentoGerenciado);
        copia.setCategoriaPai(novaCategoriaPai);
        copia.setCriadoEm(LocalDateTime.now());
        copia.setAtualizadoEm(LocalDateTime.now());

        // Clonar subcategorias, se houver
        if (original.getSubcategorias() != null && !original.getSubcategorias().isEmpty()) {
            List<CategoriaProduto> subcategoriasClonadas = original.getSubcategorias().stream()
                    .map(sub -> clonarCategoria(sub, estabelecimentoGerenciado, copia))
                    .collect(Collectors.toList());
            copia.setSubcategorias(subcategoriasClonadas);
        } else {
            // Clonar produtos apenas nos nós folha
            if (original.getProdutos() != null && !original.getProdutos().isEmpty()) {
                List<Produto> produtosClonados = original.getProdutos().stream().map(p -> {
                    Produto novoProduto = new Produto();
                    novoProduto.setNome(p.getNome());
                    novoProduto.setPreco(p.getPreco());
                    novoProduto.setDescricao(p.getDescricao());
                    novoProduto.setImagem(p.getImagem());
                    novoProduto.setOrigemCadastro(p.getOrigemCadastro());
                    novoProduto.setSituacao(p.getSituacao());
                    novoProduto.setQuantidadeVendida(p.getQuantidadeVendida());
                    novoProduto.setEstabelecimento(estabelecimentoGerenciado);
                    novoProduto.setCategoriaProduto(copia);
                    return novoProduto;
                }).collect(Collectors.toList());
                copia.setProdutos(produtosClonados);
            }
        }

        return copia;
    }

}
