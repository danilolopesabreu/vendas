package br.com.hostelpro.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoriaProdutoDTO {
	private Integer id;
	
	@NotNull
	private Integer estabelecimentoId;

	@NotBlank
	@Size(max = 100)
	private String nome;

	private Integer categoriaPaiId;
	
	private List<CategoriaProdutoDTO> subcategorias = new ArrayList<>();

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

	public List<CategoriaProdutoDTO> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<CategoriaProdutoDTO> subcategorias) {
		this.subcategorias = subcategorias;
	}
}
