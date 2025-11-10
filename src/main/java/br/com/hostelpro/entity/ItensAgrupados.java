package br.com.hostelpro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "itens_agrupados")
public class ItensAgrupados {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itens_agrupados_seq")
    @SequenceGenerator(name = "itens_agrupados_seq", sequenceName = "itens_agrupados_id_seq", allocationSize = 120)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "estabelecimento_id")
	private Estabelecimento estabelecimento;
	
	@ManyToOne
	@JoinColumn(name = "tipo_estabelecimento_id")
	private TipoEstabelecimento tipoEstabelecimento;
	
	@Column
	private String nome;

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

	public TipoEstabelecimento getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public void setTipoEstabelecimento(TipoEstabelecimento tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
