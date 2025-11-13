package br.com.hostelpro.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;

	@ManyToOne
	@JoinColumn(name = "item_agrupado_id")
	private ItensAgrupados itensAgrupados;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@Column(length = 20)
	private String status = "aberto";

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> itens = new ArrayList<>();

	@Column
	private String nomeCliente;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column
	private String numeroDoPedido;

	// getters/setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens.clear();
		if (itens != null) {
			itens.forEach(this::addItem);
		}
	}

	public void addItem(ItemPedido item) {
		item.setPedido(this);
		this.itens.add(item);
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNumeroDoPedido() {
		return numeroDoPedido;
	}

	public void setNumeroDoPedido(String numeroDoPedido) {
		this.numeroDoPedido = numeroDoPedido;
	}

	public ItensAgrupados getItensAgrupados() {
		return itensAgrupados;
	}

	public void setItensAgrupados(ItensAgrupados itensAgrupados) {
		this.itensAgrupados = itensAgrupados;
	}
}
