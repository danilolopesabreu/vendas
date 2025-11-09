package br.com.hostelpro.dto;

public class PermissaoDTO {

    private Integer id;
    private String nome;

    // não incluímos PapelDTO para evitar recursividade
    // (papel → permissao → papel → permissao …)

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
}