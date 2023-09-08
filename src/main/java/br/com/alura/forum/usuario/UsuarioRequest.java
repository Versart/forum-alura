package br.com.alura.forum.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UsuarioRequest(
        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        @Email
        String email,
        @NotNull
        @NotBlank
        String senha) {

}
