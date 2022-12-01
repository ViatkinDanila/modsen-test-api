package com.modsen.core.service.exception;

public class NotFoundEntityException extends RuntimeException{
    private Object invalidValue;
    public NotFoundEntityException() {
        super();
    }

    public NotFoundEntityException(String message) {
        super(message);
    }

    public NotFoundEntityException(String message, Object invalidValue) {
        super(message);
        this.invalidValue = invalidValue;
    }

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundEntityException(Throwable cause) {
        super(cause);
    }

    public Object getInvalidValue(){
        return invalidValue;
    }
}
