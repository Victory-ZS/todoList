package com.rest.springbootemployee.exception;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Not Found");
    }
}
