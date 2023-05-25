package br.com.alura.forum.topico;

public record TopicoResponse(Long id, String titulo, String autor, String curso) {

    public TopicoResponse(Topico topico){
        this(topico.getId(),topico.getTitulo(), topico.getAutor().getNome(),topico.getCurso().getNome());
    }
}
