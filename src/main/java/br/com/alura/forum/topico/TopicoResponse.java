package br.com.alura.forum.topico;

import br.com.alura.forum.model.Topico;

public record TopicoResponse(String titulo, String autor, String curso) {

    public TopicoResponse(Topico topico){
        this(topico.getTitulo(), topico.getAutor().getNome(),topico.getCurso().getNome());
    }
}
