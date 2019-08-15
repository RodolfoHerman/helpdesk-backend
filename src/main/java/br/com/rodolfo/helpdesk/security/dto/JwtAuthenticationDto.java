package br.com.rodolfo.helpdesk.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * JwtAuthenticationDto
 */
public class JwtAuthenticationDto {

    private String email;
    private String senha;

    public JwtAuthenticationDto() {}

    @NotEmpty(message = "Email não pode ser vazio.")
    @Email(message = "Email iválido.")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Senha não pode ser vazia.")
    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}