package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.repository.InMemoryCompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final InMemoryCompanyRepository companyRepository ;
    private final CompanyService companyService;
    public CompanyController(InMemoryCompanyRepository companyRepository, CompanyService companyService){
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> findAllCompanies(){
        return companyService.findAllCompanies();
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable int id){
        return companyService.findCompanyById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> findEmployeesById(@PathVariable int id){
        return companyService.findEmployeesById(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> findCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
        return companyService.findAllCompaniesByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyService.insertCompany(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@PathVariable int id, @RequestBody Company companyToUpdate){
        return companyService.updateCompany(id, companyToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable int id){
        companyService.deleteCompanyById(id);
    }

}
