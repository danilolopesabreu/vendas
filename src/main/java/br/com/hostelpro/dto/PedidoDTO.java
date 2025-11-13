package br.com.hostelpro.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class PedidoDTO {
	private Integer id;

	@NotNull
	private Integer estabelecimentoId;

	@NotNull
	private Integer usuarioId;

	private LocalDateTime dataCriacao;

	private String status;

	private List<ItemPedidoDTO> itens;

	private String nomeCliente;
	
	private Integer clienteId;
	
	private String numeroDoPedido;
	
	private ItensAgrupadosDTO itensAgrupados;
	
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

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
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

	public List<ItemPedidoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public ItensAgrupadosDTO getItensAgrupados() {
		return itensAgrupados;
	}

	public void setItensAgrupados(ItensAgrupadosDTO itensAgrupados) {
		this.itensAgrupados = itensAgrupados;
	}

	public String getNumeroDoPedido() {
		return numeroDoPedido;
	}

	public void setNumeroDoPedido(String numeroDoPedido) {
		this.numeroDoPedido = numeroDoPedido;
	}

}
