package com.rest.springbootemployee.company;

import com.rest.springbootemployee.Employee;
import com.rest.springbootemployee.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    public List<Company> companies;

    public CompanyRepository(){
        companies = new ArrayList<>();
        companies.add(new Company(1, "spring", new Employee(1,"Lily1",12,"male",1000)));
        companies.add(new Company(2, "spring", new Employee(2,"Lily2",23,"female",2000)));
        companies.add(new Company(3, "spring", new Employee(3,"Lily3",34,"male",3000)));
        companies.add(new Company(4, "spring", new Employee(4,"Lily4",45,"female",4000)));

    }
    public List<Company> findAllCompanies() {
        return companies;
    }

    public Company findCompanyById(int id) {
        return companies.stream()
                .filter(company -> id == company.getId())
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }
}
