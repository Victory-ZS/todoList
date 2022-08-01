package com.rest.springbootemployee.controller.dto;

import com.rest.springbootemployee.entity.Employee;

import javax.persistence.*;
import java.util.List;

public class CompanyRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String companyName;
    @OneToMany
    @JoinColumn(name = "companyId")
    private List<Employee> employees;

    public CompanyRequest(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
