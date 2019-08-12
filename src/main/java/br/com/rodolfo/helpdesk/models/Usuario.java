package br.com.rodolfo.helpdesk.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.rodolfo.helpdesk.enums.PerfilEnum;

/**
 * Usuario
 */
@Document
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email requerido!")
    private String email;

    @NotBlank(message = "Senha requerida!")
    @Size(min = 6)
    private String senha;

    private PerfilEnum perfil;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilEnum getPerfil() {
        return this.perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }
    
}