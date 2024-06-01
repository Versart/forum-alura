package br.com.alura.forum.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CursoRequest(
        @NotBlank
        @NotNull
        String nome,
        @NotNull
        @NotBlank
        String categoria) {
}
