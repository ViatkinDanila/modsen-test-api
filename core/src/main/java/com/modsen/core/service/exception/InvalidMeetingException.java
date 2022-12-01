package com.modsen.core.service.exception;

public class InvalidMeetingException extends RuntimeException{
    public InvalidMeetingException() {
        super();
    }
    public InvalidMeetingException(Exception e) {
        super(e);
    }
    public InvalidMeetingException(String message){
        super(message);
    }
    public InvalidMeetingException(String message, Exception e){
        super(message, e);
    }
}
