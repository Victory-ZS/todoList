package com.rest.springbootemployee.company;

import com.rest.springbootemployee.Employee;

public class Company {
    private int id;
    private String companyName;
    private Employee employees;

    public Company(int id, String companyName, Employee employees) {
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

    public Employee getEmployees() {
        return employees;
    }

    public void setEmployees(Employee employees) {
        this.employees = employees;
    }
}
