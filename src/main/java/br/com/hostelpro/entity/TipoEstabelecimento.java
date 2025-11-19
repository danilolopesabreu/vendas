package br.com.hostelpro.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.hostelpro.enumeradores.TipoEstabelecimentoEnum;
import jakarta.persistence.CascadeType;
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
@Table(name = "tipo_estabelecimento")
public class TipoEstabelecimento {
	
	public static final int ID_MODELO = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String nome;

	@ManyToOne
	@JoinColumn(name = "agrupador_id")
	private Agrupador agrupador;
	
	@OneToMany(mappedBy = "tipoEstabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Estabelecimento> estabelecimentos = new ArrayList<>();

	@OneToMany(mappedBy = "tipoEstabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItensAgrupados> itensAgrupados = new ArrayList<>();
	
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

	public List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public Agrupador getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(Agrupador agrupador) {
		this.agrupador = agrupador;
	}

	public List<ItensAgrupados> getItensAgrupados() {
		return itensAgrupados;
	}

	public void setItensAgrupados(List<ItensAgrupados> itensAgrupados) {
		this.itensAgrupados = itensAgrupados;
	}
	
	public TipoEstabelecimentoEnum getTipoEstabelecimentoEnum() {
		return TipoEstabelecimentoEnum.fromCodigo(this.id);
	}

}
