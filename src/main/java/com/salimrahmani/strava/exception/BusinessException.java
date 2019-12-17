package com.salimrahmani.strava.exception;

public class BusinessException extends BaseRuntimeException {

    public BusinessException(String messageKey, Object[] args) {
        super(messageKey, args);
    }
}

