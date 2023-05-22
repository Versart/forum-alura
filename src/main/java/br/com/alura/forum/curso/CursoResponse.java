package br.com.alura.forum.curso;

public record CursoResponse(Long id, String nome, String categoria) {

    public CursoResponse(Curso curso){
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
