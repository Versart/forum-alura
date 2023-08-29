package br.com.alura.forum.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.forum.usuario.Usuario;

public class UsuarioCreator {
    

    public static Usuario createUserToBeSaved() {
        return Usuario.builder()
            .email("maria@gmail.com")
            .nome("Maria")
            .senha(new BCryptPasswordEncoder().encode("12345678"))
            .build();
    }
}
