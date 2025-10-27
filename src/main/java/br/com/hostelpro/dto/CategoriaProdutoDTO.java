package br.com.hostelpro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaProdutoDTO {
	private Integer id;
	private Integer estabelecimentoId;

	@NotBlank
	@Size(max = 100)
	private String nome;

	private Integer categoriaPaiId;

	// getters/setters
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

	public Integer getCategoriaPaiId() {
		return categoriaPaiId;
	}

	public void setCategoriaPaiId(Integer categoriaPaiId) {
		this.categoriaPaiId = categoriaPaiId;
	}
}
