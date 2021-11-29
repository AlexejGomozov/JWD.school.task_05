package com.javacource.task4.exception;

public class ThreadException extends Exception{
    public ThreadException(){
    }
    public ThreadException(String message){
        super(message);
    }
    public ThreadException(String message, Throwable cause){
        super(message, cause);
    }
    public ThreadException(Throwable cause){
        super(cause);
    }
}
