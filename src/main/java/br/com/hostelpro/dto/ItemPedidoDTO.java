package br.com.hostelpro.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ItemPedidoDTO {
	private Integer id;

	@NotNull
	private Integer produtoId;

	@NotNull
	@Positive
	private Integer quantidade;

	@NotNull
	private BigDecimal precoUnitario;

	@NotNull
	private BigDecimal precoTotal;

	private ProdutoEstabelecimentoDTO produtoEstabelecimento;
	
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public ProdutoEstabelecimentoDTO getProdutoEstabelecimento() {
		return produtoEstabelecimento;
	}

	public void setProdutoEstabelecimento(ProdutoEstabelecimentoDTO produtoEstabelecimento) {
		this.produtoEstabelecimento = produtoEstabelecimento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}