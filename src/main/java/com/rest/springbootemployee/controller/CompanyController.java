package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.dto.CompanyRequest;
import com.rest.springbootemployee.controller.dto.CompanyResponse;
import com.rest.springbootemployee.controller.mapper.CompanyMapper;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper){
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping
    public List<CompanyResponse> findAllCompanies(){
        return companyService.findAllCompanies()
                .stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponse findCompanyById(@PathVariable int id){
        return companyMapper.toResponse(companyService.findCompanyById(id));
    }

    @GetMapping("/{id}/employees")
    public List<Employee> findEmployeesById(@PathVariable int id){
        return companyService.findEmployeesById(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> findCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
        return companyService.findAllCompaniesByPage(page, pageSize)
                .stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createCompany(@RequestBody CompanyRequest companyRequest){
        return companyMapper.toResponse(companyService.insertCompany(companyMapper.toEntity(companyRequest)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyResponse updateCompany(@PathVariable int id, @RequestBody CompanyRequest companyRequest){
        return companyMapper.toResponse(companyService.updateCompany(id, companyMapper.toEntity(companyRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyById(@PathVariable int id){
        companyService.deleteCompanyById(id);
    }

}
