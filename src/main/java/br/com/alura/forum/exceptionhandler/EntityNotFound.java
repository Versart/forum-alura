package br.com.alura.forum.exceptionhandler;

public class EntityNotFound extends RuntimeException{

    public EntityNotFound(String msg){
        super(msg);
    }
    public EntityNotFound() {

    }
}
