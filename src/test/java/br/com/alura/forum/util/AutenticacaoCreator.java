package br.com.alura.forum.util;

import br.com.alura.forum.usuario.DadosAutenticacao;

public class AutenticacaoCreator {
    
    public static DadosAutenticacao createDadosAutenticacao() {
        return DadosAutenticacao.builder()
            .email("maria@gmail.com")
            .senha("12345678")
            .build();
    }
}
