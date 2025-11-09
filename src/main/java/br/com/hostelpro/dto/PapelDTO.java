package br.com.hostelpro.dto;

import java.util.List;

public class PapelDTO {
    
    private Integer id;
    private String nome;
    private List<PermissaoDTO> permissoes;

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

    public List<PermissaoDTO> getPermissoes() {
        return permissoes;
    }
    public void setPermissoes(List<PermissaoDTO> permissoes) {
        this.permissoes = permissoes;
    }
}