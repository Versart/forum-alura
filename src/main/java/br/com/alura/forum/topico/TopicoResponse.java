package br.com.alura.forum.topico;

import lombok.Builder;

@Builder
public record TopicoResponse(Long id, String titulo, String status,String autor, String curso) {

    public TopicoResponse(Topico topico){
        this(topico.getId(),topico.getTitulo(), topico.getStatusTopico().toString(), topico.getAutor().getNome(),topico.getCurso().getNome());
    }
}
