package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.repository.CompanyRepository;

import java.util.List;

public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAllCompanies();
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
}
