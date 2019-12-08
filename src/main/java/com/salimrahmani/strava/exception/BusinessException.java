package com.salimrahmani.strava.exception;

public class BusinessException extends RuntimeException {

    private String messageKey;
    private Object[] args;

    public BusinessException() {
        super();
    }

    public BusinessException(String messageKey, Object[] args) {
        this.messageKey = messageKey;
        this.args = args;
    }


}
