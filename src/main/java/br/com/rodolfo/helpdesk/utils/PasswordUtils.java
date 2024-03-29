package br.com.rodolfo.helpdesk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * PasswordUtils
 */
public class PasswordUtils {

    private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);

    private PasswordUtils() {}

    /**
     * Método responsável pela geração do hash com BCrypt
     * @param senha
     * @return String
     */
    public static String gerarBCrypt(String senha) {
        
        if(senha == null) {

            return senha;
        }

        log.info("Gerando hash com BCrypt");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.encode(senha);
    }

}