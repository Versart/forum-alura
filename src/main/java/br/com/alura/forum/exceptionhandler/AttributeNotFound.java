package br.com.alura.forum.exceptionhandler;

public class AttributeNotFound extends RuntimeException{

    public AttributeNotFound(String msg) {
        super(msg);
    }
    public AttributeNotFound() {

    }
}
