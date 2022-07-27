package com.rest.springbootemployee.repository;

public class CompanyNotFoundException extends RuntimeException{
    public CompanyNotFoundException() {
        super("Not Found");
    }
}
