package com.opitzconsulting.fluentdomainfactorydemo.factory;

public class InvalidDemoDataException  extends RuntimeException{
    private final String message;

    public InvalidDemoDataException(String message) {

        this.message = message;
    }
}
