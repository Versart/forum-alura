package br.com.alura.forum.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.forum.usuario.Usuario;
import br.com.alura.forum.usuario.UsuarioRequest;

public class UsuarioCreator {
    
    private static String nome = "Maria";
    private static String email = "maria@gmail.com";

    private static String senha = "12345678";

    public static Usuario createUserToBeSaved() {
        return Usuario.builder()
            .email(email)
            .nome(nome)
            .senha(new BCryptPasswordEncoder().encode(senha))
            .build();
    }

    public static Usuario createValidUser() {
        return Usuario.builder()
            .id(1l)
            .email(email)
            .nome(nome)
            .senha(new BCryptPasswordEncoder().encode(senha))
            .build();
    }

    public static UsuarioRequest createUserRequest() {
        return UsuarioRequest.builder()
            .email(email)
            .senha(senha)
            .build();
    }
}
