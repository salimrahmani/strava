package com.salimrahmani.strava.exception;


public class NotFoundException extends BaseRuntimeException {

    public NotFoundException(String messageKey, Object[] args) {
        super(messageKey, args);
    }
}