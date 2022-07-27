package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    public List<Company> companies;

    public CompanyRepository(){
        companies = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        companies.add(new Company(1, "spring", employees));
        companies.add(new Company(2, "summer", employees));
        companies.add(new Company(3, "autumn", employees));
        companies.add(new Company(4, "winter", employees));

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

    public List<Employee> findEmployeesById(int id) {
        return findCompanyById(id).getEmployees();
    }

    public List<Company> findAllCompaniesByPage(int page, int pageSize) {
        return companies.stream()
                .skip((long)(page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company insertCompany(Company company) {
        company.setId(generateNewId());
        companies.add(company);
        return company;
    }

    private int generateNewId() {
        int max = companies.stream()
                .mapToInt(Company::getId).max()
                .orElse(0);
        return max + 1;
    }

    public Company updateCompany(int id, Company companyToUpdate) {
        if (companyToUpdate.getCompanyName() != null){
            this.findCompanyById(id).setCompanyName(companyToUpdate.getCompanyName());
        }
        if (companyToUpdate.getEmployees() != null){
            this.findCompanyById(id).setEmployees(companyToUpdate.getEmployees());
        }
        return this.findCompanyById(id);
    }

    public void deleteCompanyById(int id) {
        Company company = this.findCompanyById(id);
        companies.remove(company);
    }

    public void clearAll() {
        companies.clear();
    }
}
