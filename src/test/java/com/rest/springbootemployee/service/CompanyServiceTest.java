package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.CompanyNotFoundException;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import com.rest.springbootemployee.repository.InMemoryCompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyJpaRepository companyJpaRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_all_when_find_all_given_companies() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000,22));
        employees.add(new Employee(2,"Lily2",23,"female",2000,90));
        List<Company> preparedCompany = new ArrayList<>();
        Company firstCompany = new Company(1, "spring", employees);
        Company secondCompany = new Company(2, "summer", employees);
        preparedCompany.add(firstCompany);
        preparedCompany.add(secondCompany);
        given(companyJpaRepository.findAll()).willReturn(preparedCompany);

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
        given(companyJpaRepository.findById(1)).willReturn(Optional.of(firstCompany));

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
        given(companyJpaRepository.findAll(PageRequest.of(1, 2))).willReturn(new PageImpl<>(preparedCompanies));

        //when
        List<Company>  companies = companyService.findAllCompaniesByPage(1,2);

        //then
        assertEquals(preparedCompanies, companies);

    }

    @Test
    void should_create_company_when_perform_get_given_company() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        Company firstCompany = new Company(1, "spring", employees);

        given(companyJpaRepository.save(firstCompany)).willReturn(firstCompany);

        //when
        Company  newCompany = companyService.insertCompany(firstCompany);

        //then
        assertEquals(firstCompany, newCompany);

    }

    @Test
    void should_update_company_when_perform_get_given_company() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        Company firstCompany = new Company(1, "spring", employees);
        Company secondCompany = new Company(1, "summer", employees);

        given(companyJpaRepository.findById(1)).willReturn(Optional.of(firstCompany));

        //when
        Company  newCompany = companyService.updateCompany(1,secondCompany);

        //then
        assertEquals(firstCompany, newCompany);

    }

    @Test
    void should_delete_company_when_perform_get_given_id() {
        //given
        given(companyJpaRepository.existsById(1)).willReturn(true);

        //when
        companyService.deleteCompanyById(1);

        //then
        verify(companyJpaRepository,times(1)).deleteById(1);

    }

    @Test
    void should_throw_CompanyNotFoundException_when_delete_company_given_not_exist_id() {
        //given
        given(companyJpaRepository.existsById(anyInt())).willReturn(false);

        //when

        //then
        assertThrows(CompanyNotFoundException.class,() -> companyService.deleteCompanyById(9999));

    }

    @Test
    void should_get_employees_by_id_1_when_perform_get_given_companies() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee(1,"Lily1",12,"male",1000);
        employees.add(employee);
        Company company = new Company(1, "spring", employees);
        given(companyJpaRepository.findEmployeesById(1)).willReturn(employees);

        //when
        List<Employee>  newEmployees = companyService.findEmployeesById(1);

        //then
        assertEquals(employees,newEmployees);

    }


}
