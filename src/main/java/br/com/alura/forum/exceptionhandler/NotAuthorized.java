package br.com.alura.forum.exceptionhandler;

public class NotAuthorized extends RuntimeException{

    public NotAuthorized(String msg){
        super(msg);
    }
    public NotAuthorized() {

    }
}
