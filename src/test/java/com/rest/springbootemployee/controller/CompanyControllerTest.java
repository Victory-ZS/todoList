package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", 1))     //request

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

        //then

    }

}
