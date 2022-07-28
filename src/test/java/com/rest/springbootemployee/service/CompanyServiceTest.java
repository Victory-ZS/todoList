package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_when_find_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        List<Company> preparedCompany = new ArrayList<>();
        Company firstCompany = new Company(1, "spring", employees);
        Company secondCompany = new Company(2, "summer", employees);
        preparedCompany.add(firstCompany);
        preparedCompany.add(secondCompany);
        given(companyRepository.findAllCompanies()).willReturn(preparedCompany);

        //when
        List<Company>  companies = companyService.findAllCompanies();

        //then
        assertEquals(2, companies.size());
        assertEquals(firstCompany, companies.get(0));
    }

    @Test
    void should_return_a_company_by_id_1_when_find_all_given_companies() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        Company firstCompany = new Company(1, "spring", employees);
        Company secondCompany = new Company(2, "summer", employees);
        given(companyRepository.findCompanyById(1)).willReturn(firstCompany);

        //when
        Company  company = companyService.findCompanyById(1);

        //then
        assertEquals(firstCompany, company);

    }

    @Test
    void should_get_companies_when_perform_get_given_page_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        Company firstCompany = new Company(1, "spring", employees);
        Company secondCompany = new Company(2, "summer", employees);
        List<Company> preparedCompanies = new ArrayList<>();
        preparedCompanies.add(firstCompany);
        preparedCompanies.add(secondCompany);
        given(companyRepository.findAllCompaniesByPage(1, 2)).willReturn(preparedCompanies);

        //when
        List<Company>  companies = companyService.findAllCompaniesByPage(1,2);

        //then
        assertEquals(preparedCompanies, companies);

    }



}
