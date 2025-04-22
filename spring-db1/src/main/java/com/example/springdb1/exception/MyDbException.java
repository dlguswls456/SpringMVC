package com.example.springdb1.exception;

/**
 * 런타임(언체크) 예외
 */
public class MyDbException extends RuntimeException {

    public MyDbException() {

    }

    public MyDbException(String message) {
        super(message);
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }

}
