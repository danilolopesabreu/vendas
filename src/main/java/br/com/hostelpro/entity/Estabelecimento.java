package br.com.hostelpro.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estabelecimentos")
public class Estabelecimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 150)
	private String nome;

	@Column(length = 20)
	private String cnpj;

	@Column(length = 100)
	private String email;

	@Column(length = 20)
	private String telefone;

	@Column(length = 200)
	private String endereco;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Usuario> usuarios = new ArrayList<>();
	
	@OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CategoriaProduto> categoriaProduto = new ArrayList<>();
	
	@Column
	private int idTipoEstabelecimento;
	
	// getters e setters
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

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public int getIdTipoEstabelecimento() {
		return idTipoEstabelecimento;
	}

	public void setIdTipoEstabelecimento(int idTipoEstabelecimento) {
		this.idTipoEstabelecimento = idTipoEstabelecimento;
	}
}
