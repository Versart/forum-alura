package br.com.alura.forum.util;

import java.time.LocalDateTime;

import br.com.alura.forum.topico.StatusTopico;
import br.com.alura.forum.topico.Topico;
import br.com.alura.forum.topico.TopicoRequest;

public class TopicoCreator {
    
    public static Topico createTopicToBeSaved() {

        return Topico.builder()
        .titulo("Testando aplicacao")
        .mensagem("Criando topico para testes")
        .statusTopico(StatusTopico.NAO_RESPONDIDO)
        .dataCriacao(LocalDateTime.now())
        .build();
    }

    public static TopicoRequest createTopicRequest() {

        return TopicoRequest.builder()
            .curso("Java")
            .titulo("Como adicionar lib?")
            .mensagem("Quero utilizar o mysql no java,como eu adiciono?")
            .build();
    }
}
