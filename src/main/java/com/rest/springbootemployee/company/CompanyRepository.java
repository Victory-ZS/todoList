package com.rest.springbootemployee.company;

import com.rest.springbootemployee.Employee;
import com.rest.springbootemployee.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Employee> findEmployeesById(int id) {
        return companies.stream().
                filter(company -> company.getId() == id).map(Company::getEmployees)
                .collect(Collectors.toList());
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
                .orElseThrow(EmployeeNotFoundException::new);
        return max + 1;
    }

    public Company updateCompany(int id, Company companyToUpdate) {
        Company existingCompany = this.findCompanyById(id);
        existingCompany = companyToUpdate;
        return existingCompany;
    }

    public void deleteCompanyById(int id) {
        Company company = this.findCompanyById(id);
        companies.remove(company);
    }
}
