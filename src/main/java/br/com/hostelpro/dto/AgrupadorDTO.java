package br.com.hostelpro.dto;

import java.util.ArrayList;
import java.util.List;

public class AgrupadorDTO {

	private Integer id;
	private String nome;
	private String observacao;
	private List<TipoEstabelecimentoDTO> tipoEstabelecimento = new ArrayList<>();

	// Getters e Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<TipoEstabelecimentoDTO> getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public void setTipoEstabelecimento(List<TipoEstabelecimentoDTO> tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}