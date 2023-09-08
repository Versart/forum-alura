package br.com.alura.forum.usuario;

import lombok.Builder;

@Builder
public record DadosAutenticacao(String email, String senha) {
}
