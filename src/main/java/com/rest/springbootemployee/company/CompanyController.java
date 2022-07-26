package com.rest.springbootemployee.company;

import com.rest.springbootemployee.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    CompanyRepository companyRepository ;
    public CompanyController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Company> findAllCompanies(){
        return companyRepository.findAllCompanies();
    }

    @GetMapping("/{id}")
    public Company findCompanyById(@PathVariable int id){
        return companyRepository.findCompanyById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> findEmployeesById(@PathVariable int id){
        return companyRepository.findEmployeesById(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> findCompaniesByPage(int page, int pageSize){
        return companyRepository.findAllCompaniesByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyRepository.insertCompany(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@PathVariable int id, @RequestBody Company companyToUpdate){
        return companyRepository.updateCompany(id, companyToUpdate);
    }

}
