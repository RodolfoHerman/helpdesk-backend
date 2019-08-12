package br.com.rodolfo.helpdesk.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.rodolfo.helpdesk.enums.StatusEnum;

/**
 * AlteracaoStatus
 */
@Document
public class AlteracaoStatus {

    @Id
    private String id;

    @DBRef
    private Ticket ticket;

    @DBRef
    private Usuario usuarioAlteracao;
    
    private Date dataMudancaStatus;

    private StatusEnum status;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Usuario getUsuarioAlteracao() {
        return this.usuarioAlteracao;
    }

    public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public Date getDataMudancaStatus() {
        return this.dataMudancaStatus;
    }

    public void setDataMudancaStatus(Date dataMudancaStatus) {
        this.dataMudancaStatus = dataMudancaStatus;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    
}