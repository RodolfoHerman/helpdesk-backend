package br.com.rodolfo.helpdesk.services;

import org.springframework.data.domain.Page;

import br.com.rodolfo.helpdesk.models.Usuario;

/**
 * UsuarioService
 */
public interface UsuarioService {

    Usuario findByEmail(String email);
    
    Usuario createOrUpdate(Usuario usuario);

    Usuario findById(String id);

    void delete(String id);

    Page<Usuario> findAll(int pag, int qtd);
}