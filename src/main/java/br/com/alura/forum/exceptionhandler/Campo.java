package br.com.alura.forum.exceptionhandler;

import org.springframework.validation.FieldError;

public record Campo(String nome, String mensagem) {
    public Campo(FieldError fieldError){
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }

}
