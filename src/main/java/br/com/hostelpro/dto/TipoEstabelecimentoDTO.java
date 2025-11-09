package br.com.hostelpro.dto;

public class TipoEstabelecimentoDTO {

    private Integer id;
    private String nome;
    private AgrupadorDTO agrupador;

    // Construtores
    public TipoEstabelecimentoDTO() {}

    public TipoEstabelecimentoDTO(Integer id, String nome, AgrupadorDTO agrupador) {
        this.id = id;
        this.nome = nome;
        this.agrupador = agrupador;
    }

    // Getters e Setters
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

    public AgrupadorDTO getAgrupador() {
        return agrupador;
    }

    public void setAgrupador(AgrupadorDTO agrupador) {
        this.agrupador = agrupador;
    }
}