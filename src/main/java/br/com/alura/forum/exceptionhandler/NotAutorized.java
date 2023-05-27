package br.com.alura.forum.exceptionhandler;

public class NotAutorized extends RuntimeException{

    public NotAutorized(String msg){
        super(msg);
    }
    public NotAutorized() {

    }
}
