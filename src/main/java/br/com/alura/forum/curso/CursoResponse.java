package br.com.alura.forum.curso;

import lombok.Builder;

@Builder
public record CursoResponse(Long id, String nome, String categoria) {

    public CursoResponse(Curso curso){
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
