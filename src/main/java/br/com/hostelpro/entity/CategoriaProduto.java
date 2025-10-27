package br.com.hostelpro.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "categoria_produto")
public class CategoriaProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
//	@MapsId("id")
	private Estabelecimento estabelecimento;

	@Column(nullable = false, length = 100)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "categoria_pai_id")
//	@MapsId("id")
	private CategoriaProduto categoriaPai;
	
	@OneToMany(mappedBy = "categoriaPai")
	private List<CategoriaProduto> subcategorias = new ArrayList<>();

	@Column(name = "criado_em")
	private LocalDateTime criadoEm = LocalDateTime.now();

	@Column(name = "atualizado_em")
	private LocalDateTime atualizadoEm = LocalDateTime.now();

	public CategoriaProduto() {
	}
	
	public CategoriaProduto(Integer id) {
	    this.id = id;
	}
	
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CategoriaProduto getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoriaProduto categoriaPai) {
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

	public List<CategoriaProduto> getSubcategorias() {
		return subcategorias;
	}

	public void setSubcategorias(List<CategoriaProduto> subcategorias) {
		this.subcategorias = subcategorias;
	}
}
