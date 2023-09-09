package br.com.alura.forum.topico;

import lombok.Builder;

@Builder
public record AlteredTopic(String titulo, String mensagem, String curso) {

}
