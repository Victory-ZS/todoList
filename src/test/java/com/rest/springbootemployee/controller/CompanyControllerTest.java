package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    MockMvc client;

    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void clearCompanyInRepository(){   //first do
        companyRepository.clearAll();
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        companyRepository.insertCompany(new Company(1, "spring", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies"))     //request
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("spring"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees").value(employees.get(0)))
        ;

        //then

    }

    @Test
    void should_get_company_by_id_1_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        companyRepository.insertCompany(new Company(1, "spring", employees));
        companyRepository.insertCompany(new Company(2, "summer", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", 1))     //request

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

        //then
    }

    @Test
    void should_get_employees_by_id_1_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
        companyRepository.insertCompany(new Company(1, "spring", employees));

        //when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", 1))     //request

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
    void should_get_companies_when_perform_get_page_pageSize() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",12,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",2000));
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
    void should_create_company_when_perform_get_company() throws Exception {
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("spring"));

        //then
        List<Company> companies = companyRepository.findAllCompanies();
        assertThat(companies, hasSize(1));
        assertThat(companies.get(0).getId(), equalTo(1));
        assertThat(companies.get(0).getCompanyName(), equalTo("spring"));
    }

}
