package br.com.alura.forum.util;

import java.time.LocalDateTime;

import br.com.alura.forum.topico.AlteredTopic;
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

    public static TopicoRequest createTopicRequestToBeSaved() {

        return TopicoRequest.builder()
            .curso("Java")
            .titulo("Como adicionar lib?")
            .mensagem("Quero utilizar o mysql no java,como eu adiciono?")
            .build();
    }

    public static TopicoRequest createTopicRequestWithoutTitulo() {
          return TopicoRequest.builder()
            .curso("Java")
            .mensagem("Quero utilizar o mysql no java,como eu adiciono?")
            .build();
    }

    public static AlteredTopic createTopicRequestAltered() {
        return AlteredTopic.builder()
            .curso("Java")
            .titulo("Como adicionar lib  no Java?")
            .mensagem("Quero utilizar o mysql no java,como eu adiciono?")
            .build();
    }

}
