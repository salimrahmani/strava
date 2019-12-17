package com.salimrahmani.strava.exception;

import lombok.Data;

@Data
public class BaseRuntimeException extends RuntimeException {
    private String messageKey;

    private Object[] args;

    public BaseRuntimeException(String messageKey, Object[] args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public BaseRuntimeException(String messageKey, Object[] args, Throwable cause) {
        super(messageKey, cause);
        this.messageKey = messageKey;
        this.args = args;
    }

    public BaseRuntimeException(String message, String messageKey, Object[] args, Throwable cause) {
        super(message, cause);
        this.messageKey = messageKey;
        this.args = args;
    }

    public BaseRuntimeException(String message, String messageKey, Object[] args) {
        super(message);
        this.messageKey = messageKey;
        this.args = args;
    }
}
