package br.com.hostelpro.dto;
import java.math.BigDecimal;

public class ProdutoEstabelecimentoDTO {

    private Integer id;
    private Integer produtoBaseId;
    private Integer estabelecimentoId;
    private Integer categoriaId;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String imagem;
    private Integer estoque;
    private Integer quantidadeVendida;
    private String situacao;
    private String origemCadastro;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProdutoBaseId() { return produtoBaseId; }
    public void setProdutoBaseId(Integer produtoBaseId) { this.produtoBaseId = produtoBaseId; }

    public Integer getEstabelecimentoId() { return estabelecimentoId; }
    public void setEstabelecimentoId(Integer estabelecimentoId) { this.estabelecimentoId = estabelecimentoId; }

    public Integer getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Integer categoriaId) { this.categoriaId = categoriaId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }

    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }

    public Integer getQuantidadeVendida() { return quantidadeVendida; }
    public void setQuantidadeVendida(Integer quantidadeVendida) { this.quantidadeVendida = quantidadeVendida; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public String getOrigemCadastro() { return origemCadastro; }
    public void setOrigemCadastro(String origemCadastro) { this.origemCadastro = origemCadastro; }
}