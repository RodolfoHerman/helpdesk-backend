package br.com.rodolfo.helpdesk.security.models;

import br.com.rodolfo.helpdesk.models.Usuario;

/**
 * CurrentUser
 */
public class CurrentUser {

    private String token;
    private Usuario usuario;

    public CurrentUser(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}