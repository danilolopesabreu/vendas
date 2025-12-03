package br.com.hostelpro.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "agrupador")
public class Agrupador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String nome;

	@Column(columnDefinition = "text")
	private String observacao;
	
	@OneToMany(mappedBy = "agrupador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TipoEstabelecimento> tipoEstabelecimento = new ArrayList<>();
	
	@Column
	private String rotulo;

	// getters/setters
	public Integer getId() {
		return id;
	}

	public List<TipoEstabelecimento> getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public void setTipoEstabelecimento(List<TipoEstabelecimento> tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
}
