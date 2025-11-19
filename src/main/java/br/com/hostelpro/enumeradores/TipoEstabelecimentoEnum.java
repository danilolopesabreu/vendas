package br.com.hostelpro.enumeradores;

import java.util.Arrays;

public enum TipoEstabelecimentoEnum {
    HOSPEDAGEM(1, "Hospedagem"),
    RESTAURANTE_LANCHONETE(2, "Restaurante/Lanchonete"),
    DELIVERY_ECOMMERCE(3, "Delivery / E-commerce de comida"),
    CAFETERIA(4, "Cafeteria");

    private final int codigo;
    private final String descricao;

    TipoEstabelecimentoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() { return codigo; }
    public String getDescricao() { return descricao; }

    public static TipoEstabelecimentoEnum fromCodigo(int codigo) {
        return Arrays.stream(values())
                .filter(tipo -> tipo.codigo == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código inválido: " + codigo));
    }
}