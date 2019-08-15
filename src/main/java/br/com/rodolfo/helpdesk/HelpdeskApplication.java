package br.com.rodolfo.helpdesk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.rodolfo.helpdesk.enums.PerfilEnum;
import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.repositories.UsuarioRepository;
import br.com.rodolfo.helpdesk.utils.PasswordUtils;

@SpringBootApplication
public class HelpdeskApplication {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		return args -> {

			String email = "usuario1@email.com";			
			Usuario temp = usuarioRepository.findByEmail(email);

			if(temp == null) {

				Usuario usuario1 = new Usuario();
				usuario1.setEmail(email);
				usuario1.setSenha(PasswordUtils.gerarBCrypt("123456"));
				usuario1.setPerfil(PerfilEnum.ROLE_ADMINISTRADOR);

				this.usuarioRepository.save(usuario1);
			}
		};
	}

}
