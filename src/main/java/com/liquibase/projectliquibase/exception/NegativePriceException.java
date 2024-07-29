package com.liquibase.projectliquibase.exception;

public class NegativePriceException extends RuntimeException {

    public NegativePriceException(String message) {
        super(message);
    }


    NegativePriceException(){

    }
}
