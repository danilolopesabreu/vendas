package br.com.hostelpro.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@Column(nullable = false)
	private Integer quantidade = 1;

	@Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal precoUnitario;

	@Column(name = "preco_total", nullable = false, precision = 10, scale = 2)
	private BigDecimal precoTotal;

	// getters/setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public java.math.BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(java.math.BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public java.math.BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(java.math.BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

}
