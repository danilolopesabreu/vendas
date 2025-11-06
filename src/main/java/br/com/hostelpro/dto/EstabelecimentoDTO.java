package br.com.hostelpro.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EstabelecimentoDTO {
	private Integer id;

	@NotBlank
	@Size(max = 150)
	private String nome;

	@Size(max = 20)
	private String cnpj;

	@Size(max = 100)
	private String email;

	@Size(max = 20)
	private String telefone;

	@Size(max = 200)
	private String endereco;
	
	private List<UsuarioDTO> usuarios;
	
	private List<CategoriaProdutoDTO> categoriaProduto;
	
	private int idTipoEstabelecimento;

	// getters/setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public List<CategoriaProdutoDTO> getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(List<CategoriaProdutoDTO> categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public int getIdTipoEstabelecimento() {
		return idTipoEstabelecimento;
	}

	public void setIdTipoEstabelecimento(int idTipoEstabelecimento) {
		this.idTipoEstabelecimento = idTipoEstabelecimento;
	}
}
