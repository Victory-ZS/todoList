package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import com.rest.springbootemployee.repository.InMemoryCompanyRepository;
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
        return companyRepository.findCompanyById(id);
    }

    public List<Company> findAllCompaniesByPage(int page, int pageSize) {
        return companyRepository.findAllCompaniesByPage(page, pageSize);
    }

    public Company insertCompany(Company company) {
        return companyRepository.insertCompany(company);
    }

    public Company updateCompany(int id, Company companyToUpdate) {
        Company company = companyRepository.findCompanyById(id);
        if (companyToUpdate.getCompanyName() != null){
            company.setCompanyName(companyToUpdate.getCompanyName());
        }
        if (companyToUpdate.getEmployees() != null){
            company.setEmployees(companyToUpdate.getEmployees());
        }
        return company;
    }

    public void deleteCompanyById(int id) {
        companyRepository.deleteCompanyById(id);
    }

    public List<Employee> findEmployeesById(int id) {
        return companyRepository.findEmployeesById(id);
    }
}
