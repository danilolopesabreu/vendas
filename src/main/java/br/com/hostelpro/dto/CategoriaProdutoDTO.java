package br.com.hostelpro.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CategoriaProdutoDTO {
	
	private Integer id;
	
	private Integer estabelecimentoId;

	private String nome;

	private CategoriaProdutoDTO categoriaPai;
	
	private List<CategoriaProdutoDTO> subcategorias = new ArrayList<>();
	
	private LocalDateTime criadoEm;

	private LocalDateTime atualizadoEm;
	
	private String imagem;
	
	private boolean associadaAoEstabelecimento;
	
    private boolean possuiVenda;

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

	public List<CategoriaProdutoDTO> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<CategoriaProdutoDTO> subcategorias) {
		this.subcategorias = subcategorias;
	}

	public CategoriaProdutoDTO getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoriaProdutoDTO categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public LocalDateTime getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(LocalDateTime atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public boolean isAssociadaAoEstabelecimento() {
		return associadaAoEstabelecimento;
	}

	public void setAssociadaAoEstabelecimento(boolean associadaAoEstabelecimento) {
		this.associadaAoEstabelecimento = associadaAoEstabelecimento;
	}

	public boolean isPossuiVenda() {
		return possuiVenda;
	}

	public void setPossuiVenda(boolean possuiVenda) {
		this.possuiVenda = possuiVenda;
	}
}
