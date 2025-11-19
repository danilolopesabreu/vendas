package br.com.hostelpro.enumeradores;

import java.util.Arrays;

public enum CategoriaProdutoEnum {
    BEBIDA_ALCOOLICA(2, "Alcoólica"), 
    BEBIDA_NAO_ALCOOLICA(3, "Não Alcoólica"),
    LANCHE_FAST_FOOD(4, "Lanches / Fast Food"),
    CAFE_DA_MANHA(5, "Café da Manhã"),
    PRATO_PRINCIPAL(6, "Pratos Principais"),
    SOBREMESA(7, "Sobremesas"),
    CONVENIENCIA(8, "Conveniência"),
    CAFES(9, "Cafés");

    private final int codigo;
    private final String descricao;

    CategoriaProdutoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static CategoriaProdutoEnum fromCodigo(int codigo) {
        return Arrays.stream(values())
                .filter(categoria -> categoria.codigo == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código inválido: " + codigo));
    }
    
    public static CategoriaProdutoEnum fromNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        
        String nomeNormalizado = normalizarTexto(nome.trim()).toLowerCase();
        
        return Arrays.stream(values())
                .filter(categoria -> normalizarTexto(categoria.descricao).toLowerCase().equals(nomeNormalizado))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nome de categoria inválido: " + nome));
    }
    
    private static String normalizarTexto(String texto) {
        return texto.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // Remove caracteres especiais
                .replaceAll("\\s+", " "); // Normaliza espaços
    }
}