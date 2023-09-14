package br.com.alura.forum.util;

import java.time.LocalDateTime;

import br.com.alura.forum.topico.AlteredTopic;
import br.com.alura.forum.topico.StatusTopico;
import br.com.alura.forum.topico.Topico;
import br.com.alura.forum.topico.TopicoRequest;
import br.com.alura.forum.topico.TopicoResponse;

public class TopicoCreator {
    
    public static Topico createTopicToBeSaved() {

        return Topico.builder()
            .titulo("Testando aplicacao")
            .mensagem("Criando topico para testes")
            .statusTopico(StatusTopico.NAO_RESPONDIDO)
            .dataCriacao(LocalDateTime.now())
            .build();
    }

    public static Topico createValidTopic() {
          return Topico.builder()
            .id(1l)
            .titulo("Testando aplicacao")
            .mensagem("Criando topico para testes")
            .statusTopico(StatusTopico.NAO_RESPONDIDO)
            .dataCriacao(LocalDateTime.now())
            .autor(UsuarioCreator.createValidUser())
            .curso(CursoCreator.createValidCourse())
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

    public static TopicoResponse createTopicResponse() {
        return TopicoResponse.builder()
            .titulo("Criar classe")
            .autor("Maria")
            .curso("Java")
            .id(1l)
            .status(StatusTopico.NAO_RESPONDIDO.toString())
            .build();
            
    }

}
