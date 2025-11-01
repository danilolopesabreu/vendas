package br.com.hostelpro.dto;

import java.math.BigDecimal;

public class ProdutoDTO {

    private Integer id;
    private Integer estabelecimentoId;
    private String nome;
    private BigDecimal preco;
    private Integer estoque;
    private Integer categoriaId;
    private String origemCadastro;
    private String imagem;
    private String descricao;
    private Integer quantidadeVendida;
    private String situacao;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Integer id, Integer estabelecimentoId, String nome, BigDecimal preco, Integer estoque,
                      Integer categoriaId, String origemCadastro, String imagem, String descricao,
                      Integer quantidadeVendida, String situacao) {
        this.id = id;
        this.estabelecimentoId = estabelecimentoId;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.categoriaId = categoriaId;
        this.origemCadastro = origemCadastro;
        this.imagem = imagem;
        this.descricao = descricao;
        this.quantidadeVendida = quantidadeVendida;
        this.situacao = situacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEstabelecimentoId() {
        return estabelecimentoId;
    }

    public void setEstabelecimentoId(Integer estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getOrigemCadastro() {
        return origemCadastro;
    }

    public void setOrigemCadastro(String origemCadastro) {
        this.origemCadastro = origemCadastro;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(Integer quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
