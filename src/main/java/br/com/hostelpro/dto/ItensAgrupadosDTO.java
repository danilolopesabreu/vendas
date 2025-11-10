package br.com.hostelpro.dto;

public class ItensAgrupadosDTO {

	private Integer id;
	private Integer estabelecimentoId;
	private Integer tipoEstabelecimentoId;
	private String nome;

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

	public Integer getTipoEstabelecimentoId() {
		return tipoEstabelecimentoId;
	}

	public void setTipoEstabelecimentoId(Integer tipoEstabelecimentoId) {
		this.tipoEstabelecimentoId = tipoEstabelecimentoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
