package br.com.hostelpro.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.enumeradores.CategoriaProdutoEnum;
import br.com.hostelpro.enumeradores.TipoEstabelecimentoEnum;

@Service
public class RelevanciaService {

    private static final int PONTUACAO_MAXIMA_CATEGORIA = 100;
    private static final int PONTUACAO_BASE = 10;
    
    private final Map<TipoEstabelecimentoEnum, List<CategoriaProdutoEnum>> relevanciaCategorias;

    public RelevanciaService() {
        this.relevanciaCategorias = inicializarRelevanciaCategorias();
    }

    private Map<TipoEstabelecimentoEnum, List<CategoriaProdutoEnum>> inicializarRelevanciaCategorias() {
        Map<TipoEstabelecimentoEnum, List<CategoriaProdutoEnum>> relevancia = new HashMap<>();
        
        relevancia.put(TipoEstabelecimentoEnum.HOSPEDAGEM, Arrays.asList(
            CategoriaProdutoEnum.BEBIDA_NAO_ALCOOLICA,
            CategoriaProdutoEnum.BEBIDA_ALCOOLICA,
            CategoriaProdutoEnum.PRATO_PRINCIPAL,
            CategoriaProdutoEnum.LANCHE_FAST_FOOD,
            CategoriaProdutoEnum.CAFE_DA_MANHA,
            CategoriaProdutoEnum.SOBREMESA,
            CategoriaProdutoEnum.CONVENIENCIA,
            CategoriaProdutoEnum.CAFES
        ));

        relevancia.put(TipoEstabelecimentoEnum.RESTAURANTE_LANCHONETE, Arrays.asList(
            CategoriaProdutoEnum.LANCHE_FAST_FOOD,
            CategoriaProdutoEnum.PRATO_PRINCIPAL,
            CategoriaProdutoEnum.SOBREMESA,
            CategoriaProdutoEnum.BEBIDA_NAO_ALCOOLICA,
            CategoriaProdutoEnum.BEBIDA_ALCOOLICA,
            CategoriaProdutoEnum.CONVENIENCIA,
            CategoriaProdutoEnum.CAFE_DA_MANHA,
            CategoriaProdutoEnum.CAFES
        ));

        relevancia.put(TipoEstabelecimentoEnum.DELIVERY_ECOMMERCE, Arrays.asList(
            CategoriaProdutoEnum.LANCHE_FAST_FOOD,
            CategoriaProdutoEnum.PRATO_PRINCIPAL,
            CategoriaProdutoEnum.SOBREMESA,
            CategoriaProdutoEnum.BEBIDA_NAO_ALCOOLICA,
            CategoriaProdutoEnum.BEBIDA_ALCOOLICA,
            CategoriaProdutoEnum.CONVENIENCIA,
            CategoriaProdutoEnum.CAFE_DA_MANHA,
            CategoriaProdutoEnum.CAFES
        ));

        relevancia.put(TipoEstabelecimentoEnum.CAFETERIA, Arrays.asList(
            CategoriaProdutoEnum.CAFES,
            CategoriaProdutoEnum.CAFE_DA_MANHA,
            CategoriaProdutoEnum.BEBIDA_NAO_ALCOOLICA,
            CategoriaProdutoEnum.LANCHE_FAST_FOOD,
            CategoriaProdutoEnum.SOBREMESA,
            CategoriaProdutoEnum.CONVENIENCIA,
            CategoriaProdutoEnum.PRATO_PRINCIPAL,
            CategoriaProdutoEnum.BEBIDA_ALCOOLICA
        ));
        
        return relevancia;
    }

    /**
     * Calcula e atribui relevância para uma lista de produtos
     */
    public List<ProdutoEstabelecimento> calcularRelevanciaParaLista(List<ProdutoEstabelecimento> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            return Collections.emptyList();
        }

        return produtos.stream()
                .map(this::calcularEAtribuirRelevancia)
                .collect(Collectors.toList());
    }

    /**
     * Calcula e atribui relevância para um único produto
     */
    public ProdutoEstabelecimento calcularEAtribuirRelevancia(ProdutoEstabelecimento produto) {
        Integer relevancia = calcularRelevancia(produto);
        produto.setRelevancia(relevancia);
        return produto;
    }

    /**
     * Ordena produtos por relevância (maior primeiro)
     */
    public List<ProdutoEstabelecimento> ordenarPorRelevancia(List<ProdutoEstabelecimento> produtos) {
        return produtos.stream()
                .sorted((p1, p2) -> {
                    int relevancia1 = p1.getRelevancia() != null ? p1.getRelevancia() : 0;
                    int relevancia2 = p2.getRelevancia() != null ? p2.getRelevancia() : 0;
                    return Integer.compare(relevancia2, relevancia1); // Ordem decrescente
                })
                .collect(Collectors.toList());
    }

    /**
     * Filtra e ordena produtos ativos por relevância
     */
    public List<ProdutoEstabelecimento> filtrarAtivosEOrdenarPorRelevancia(List<ProdutoEstabelecimento> produtos) {
        return produtos.stream()
                .filter(ProdutoEstabelecimento::isAtivo)
                .sorted((p1, p2) -> {
                    int relevancia1 = p1.getRelevancia() != null ? p1.getRelevancia() : 0;
                    int relevancia2 = p2.getRelevancia() != null ? p2.getRelevancia() : 0;
                    return Integer.compare(relevancia2, relevancia1);
                })
                .collect(Collectors.toList());
    }

    /**
     * Agrupa produtos por categoria e ordena por relevância dentro de cada categoria
     */
    public Map<CategoriaProduto, List<ProdutoEstabelecimento>> agruparPorCategoriaEOrdenar(
            List<ProdutoEstabelecimento> produtos) {
        
        return produtos.stream()
                .filter(ProdutoEstabelecimento::isAtivo)
                .collect(Collectors.groupingBy(
                    ProdutoEstabelecimento::getCategoria,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        this::ordenarPorRelevancia
                    )
                ));
    }

    /**
     * Método principal de cálculo de relevância
     */
    public Integer calcularRelevancia(ProdutoEstabelecimento produto) {
        if (!produto.isAtivo()) {
            return 0;
        }
        
        int pontuacaoCategoria = calcularPontuacaoCategoria(produto);
        int pontuacaoPopularidade = calcularPontuacaoPopularidade(produto);
        
        return pontuacaoCategoria + pontuacaoPopularidade;
    }

    private int calcularPontuacaoCategoria(ProdutoEstabelecimento produto) {
        if (produto.getCategoria() == null) {
            return PONTUACAO_BASE;
        }

        List<CategoriaProdutoEnum> categoriasOrdenadas = relevanciaCategorias.get(
            produto.getEstabelecimento().getTipoEstabelecimento().getTipoEstabelecimentoEnum()
        );
        
        if (categoriasOrdenadas == null) {
            return PONTUACAO_BASE;
        }
        
        CategoriaProdutoEnum categoriaEnum = produto.getCategoria().getCategoriaEnum();
        int posicao = categoriasOrdenadas.indexOf(categoriaEnum);
        
        if (posicao == -1) {
            return PONTUACAO_BASE;
        }
        
        return PONTUACAO_MAXIMA_CATEGORIA - (posicao * 10);
    }

    private int calcularPontuacaoPopularidade(ProdutoEstabelecimento produto) {
        int pontuacao = 0;
        String nomeLower = produto.getNome().toLowerCase();
        
        // Produtos básicos e populares
        if (contemPalavraChave(nomeLower, "água", "agua", "café", "cafe", "pão", "refrigerante")) {
            pontuacao += 30;
        }
        
        // Produtos de alta rotatividade
        if (contemPalavraChave(nomeLower, "hambúrguer", "pizza", "sanduíche", "sorvete")) {
            pontuacao += 25;
        }
        
        // Produtos premium/especiais
        if (contemPalavraChave(nomeLower, "espresso", "cappuccino", "latte", "premium", "gourmet")) {
            pontuacao += 20;
        }
        
        // Considerar estoque como indicador de demanda esperada
        if (produto.getEstoque() >= 200) {
            pontuacao += 15;
        } else if (produto.getEstoque() >= 100) {
            pontuacao += 10;
        }
        
        return pontuacao;
    }

    private boolean contemPalavraChave(String texto, String... palavrasChave) {
        return Arrays.stream(palavrasChave)
                .anyMatch(texto::contains);
    }
}