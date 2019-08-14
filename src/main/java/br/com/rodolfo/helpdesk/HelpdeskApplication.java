package br.com.rodolfo.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.rodolfo.helpdesk.enums.PerfilEnum;
import br.com.rodolfo.helpdesk.models.Usuario;
import br.com.rodolfo.helpdesk.repository.UsuarioRepository;

@SpringBootApplication
public class HelpdeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {

		return args -> {
			inicializarusuarios(usuarioRepository, passwordEncoder);
		};
	}

	private void inicializarusuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		
		Usuario admin = new Usuario();
		admin.setEmail("admin@helpdesk.com");
		admin.setSenha(passwordEncoder.encode("123456"));
		admin.setPerfil(PerfilEnum.ROLE_ADMINISTRADOR);

		Usuario find = usuarioRepository.findByEmail("admin@helpdesk.com");

		if(find == null) {

			usuarioRepository.save(admin);
		}

	}

}
