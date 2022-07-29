package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import com.rest.springbootemployee.repository.EmployeeJpaRepository;
import com.rest.springbootemployee.repository.InMemoryCompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc client;

    @Autowired
    InMemoryCompanyRepository companyRepository;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private CompanyJpaRepository companyJpaRepository;
    private Company preparedCompany;

    @BeforeEach
    void clearCompanyInRepository(){   //first do
        companyRepository.clearAll();
        companyJpaRepository.deleteAll();
        Company company = new Company();
        company.setCompanyName("cool");
        preparedCompany = companyJpaRepository.save(company);
    }

    private Employee employee1(){
        Employee employee = new Employee(null,"Lily1",12,"male",1000,preparedCompany.getId());
        return employeeJpaRepository.save(employee);
    }

    private Employee employee2(){
        Employee employee = new Employee(null,"Lily2",23,"male",2000,preparedCompany.getId());
        return employeeJpaRepository.save(employee);
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1());
        employees.add(employee2());
        companyJpaRepository.save(new Company(1, "spring", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies"))     //request
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].id").value(employees.get(0).getId()));

        //then

    }

    @Test
    void should_get_company_by_id_1_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1());
        employees.add(employee2());
        companyRepository.insertCompany(new Company(1, "spring", employees));
        companyRepository.insertCompany(new Company(2, "summer", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", preparedCompany.getId()))     //request

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

        //then
    }

    @Test
    void should_get_employees_by_id_1_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1());
        employees.add(employee2());
        companyRepository.insertCompany(new Company(1, "spring", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", preparedCompany.getId()))     //request

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lily1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(1000));

        //then
    }

    @Test
    void should_get_companies_when_perform_get_given_page_pageSize() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1());
        employees.add(employee2());
        companyRepository.insertCompany(new Company(1, "spring", employees));
        companyRepository.insertCompany(new Company(2, "summer", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies")
                        .param("page","1")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));

        //then
    }

    @Test
    void should_create_company_when_perform_get_given_company() throws Exception {
        //given
        String newCompanyJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"spring\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Lily1\",\n" +
                "            \"age\": 12,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 1000\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"Lily2\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"female\",\n" +
                "            \"salary\": 2000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when
        client.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompanyJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("spring"));

        //then
        List<Company> companies = companyRepository.findAllCompanies();
        assertThat(companies, hasSize(1));
        assertThat(companies.get(0).getId(), equalTo(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("spring"));
    }

    @Test
    void should_update_company_when_perform_get_given_company() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000,90));
        employees.add(new Employee(2,"Lily2",23,"female",2000,999));
        companyRepository.insertCompany(new Company(1, "spring", employees));
        String newCompanyJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"companyName\": \"summer\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Lily1\",\n" +
                "            \"age\": 12,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 1000\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"Lily2\",\n" +
                "            \"age\": 23,\n" +
                "            \"gender\": \"female\",\n" +
                "            \"salary\": 2000\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //when
        client.perform(MockMvcRequestBuilders.put("/companies/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompanyJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("summer"));

        //then
        List<Company> companies = companyRepository.findAllCompanies();
        assertThat(companies, hasSize(1));
        assertThat(companies.get(0).getId(), equalTo(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("summer"));
    }

    @Test
    void should_delete_company_when_perform_get_given_id() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1());
        employees.add(employee2());
        companyRepository.insertCompany(new Company(1, "spring", employees));
        companyRepository.insertCompany(new Company(2, "summer", employees));

        //when
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", preparedCompany.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //then
        List<Company> companies = companyRepository.findAllCompanies();
        assertThat(companies,hasSize(1));
    }

}
