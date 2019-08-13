package br.com.rodolfo.helpdesk.models;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.rodolfo.helpdesk.enums.PrioridadeEnum;
import br.com.rodolfo.helpdesk.enums.StatusEnum;

/**
 * Ticket
 */
@Document
public class Ticket {

    @Id
    private String id;

    @DBRef(lazy = true)
    private Usuario usuario;

    private Date data;

    private String titulo;

    private Integer numero;

    private StatusEnum status;

    private PrioridadeEnum prioridade;

    @DBRef(lazy = true)
    private Usuario usuarioDesignado;

    private String descricao;

    private String imagem;

    //Não criar tabela no banco de dados (ignorar) - anotação Transient
    @Transient
    private List<AlteracaoStatus> alteracoes;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public PrioridadeEnum getPrioridade() {
        return this.prioridade;
    }

    public void setPrioridade(PrioridadeEnum prioridade) {
        this.prioridade = prioridade;
    }

    public Usuario getUsuarioDesignado() {
        return this.usuarioDesignado;
    }

    public void setUsuarioDesignado(Usuario usuarioDesignado) {
        this.usuarioDesignado = usuarioDesignado;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return this.imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<AlteracaoStatus> getAlteracoes() {
        return this.alteracoes;
    }

    public void setAlteracoes(List<AlteracaoStatus> alteracoes) {
        this.alteracoes = alteracoes;
    }
    
}