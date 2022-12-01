package com.modsen.core.service.exception;

public class InvalidParamException extends RuntimeException{
    public InvalidParamException() {
        super();
    }
    public InvalidParamException(Exception e) {
        super(e);
    }
    public InvalidParamException(String message){
        super(message);
    }
    public InvalidParamException(String message, Exception e){
        super(message, e);
    }
}
