package br.com.alura.forum.util;

import java.time.LocalDateTime;

import br.com.alura.forum.topico.StatusTopico;
import br.com.alura.forum.topico.Topico;

public class TopicoCreator {
    
    public static Topico createTopicToBeSaved() {


        return Topico.builder()
        .titulo("Testando aplicacao")
        .mensagem("Criando topico para testes")
        .statusTopico(StatusTopico.NAO_RESPONDIDO)
        .dataCriacao(LocalDateTime.now())
        
        
        .build();
    }
}
