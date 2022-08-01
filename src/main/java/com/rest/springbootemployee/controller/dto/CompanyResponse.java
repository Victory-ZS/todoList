package com.rest.springbootemployee.controller.dto;

import com.rest.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyResponse {

    private int id;
    private String companyName;
    private List<EmployeeResponse> employees;

    public CompanyResponse(){

    }

    public CompanyResponse(int id, String companyName, List<EmployeeResponse> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public List<EmployeeResponse> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponse> employees) {
        this.employees = employees;
    }
}
