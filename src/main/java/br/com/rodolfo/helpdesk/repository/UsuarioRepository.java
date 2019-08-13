package br.com.rodolfo.helpdesk.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rodolfo.helpdesk.models.Usuario;

/**
 * UsuarioRepository
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String>{

    Usuario findByEmail(String email);
    
}