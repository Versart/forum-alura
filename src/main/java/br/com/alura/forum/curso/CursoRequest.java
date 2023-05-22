package br.com.alura.forum.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequest(
        @NotBlank
        @NotNull
        String nome,
        @NotNull
        @NotBlank
        String categoria) {
}
