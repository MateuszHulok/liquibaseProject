package com.liquibase.projectliquibase.exception;

public class PetNotFoundExpetion extends RuntimeException {

    public PetNotFoundExpetion(String message) {
        super(message);
    }

    PetNotFoundExpetion(){
    }
}
