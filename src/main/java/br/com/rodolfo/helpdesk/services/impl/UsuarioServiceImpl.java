package br.com.rodolfo.helpdesk.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.repository.UsuarioRepository;
import br.com.rodolfo.helpdesk.services.UsuarioService;

/**
 * UsuarioServiceImpl
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario createOrUpdate(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findById(String id) {
        return this.usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        this.usuarioRepository.deleteById(id);
    }

    @Override
	public Page<Usuario> findAll(int pag, int qtd) {
        return this.usuarioRepository.findAll(PageRequest.of(pag, qtd));
	}

    
}