package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.CompanyNotFoundException;
import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import com.rest.springbootemployee.repository.InMemoryCompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {

    private InMemoryCompanyRepository companyRepository;
    private CompanyJpaRepository companyJpaRepository;

    public CompanyService(InMemoryCompanyRepository companyRepository, CompanyJpaRepository companyJpaRepository) {
        this.companyRepository = companyRepository;
        this.companyJpaRepository = companyJpaRepository;
    }

    public List<Company> findAllCompanies() {
        return companyJpaRepository.findAll();
    }

    public Company findCompanyById(int id) {
        return companyJpaRepository.findById(id)
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> findAllCompaniesByPage(int page, int pageSize) {
        return companyJpaRepository.findAll(PageRequest.of(page, pageSize)).toList();
    }

    public Company insertCompany(Company company) {
        return companyJpaRepository.save(company);
    }

    public Company updateCompany(int id, Company companyToUpdate) {
        Company company = this.findCompanyById(id);
        if (companyToUpdate.getCompanyName() != null){
            company.setCompanyName(companyToUpdate.getCompanyName());
        }
        if (companyToUpdate.getEmployees() != null){
            company.setEmployees(companyToUpdate.getEmployees());
        }
        companyJpaRepository.save(company);
        return company;
    }

    public void deleteCompanyById(int id) {
        boolean exists = companyJpaRepository.existsById(id);
        if(! companyJpaRepository.existsById(id)){
            throw new CompanyNotFoundException();
        }
        companyJpaRepository.deleteById(id);
    }

    public List<Employee> findEmployeesById(int id) {
        return companyJpaRepository.findEmployeesById(id);
    }
}
