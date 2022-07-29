package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyJpaRepository;
import com.rest.springbootemployee.repository.EmployeeJpaRepository;
import com.rest.springbootemployee.repository.InMemoryEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeControllerTest {

    @Autowired
    MockMvc client;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    private CompanyJpaRepository companyJpaRepository;

    private Company preparedCompany;

    @BeforeEach
    void clearEmployeeInRepository(){   //first do
        employeeJpaRepository.deleteAll();
        companyJpaRepository.deleteAll();
        Company company = new Company();
        company.setCompanyName("cool");
        preparedCompany = companyJpaRepository.save(company);

    }

    @Test
    void should_get_all_employees_when_perform_get_given_employees() throws Exception {
        //given
        employeeJpaRepository.save(new Employee(1,"Sally", 22, "Female", 10000, preparedCompany.getId()));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees"))     //request
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000));

        //then

    }

    @Test
    void should_create_a_new_employee_when_perform_post_given_a_new_employee() throws Exception {
        //given
        String newEmployeeJson = "{\n" +
                "        \"name\": \"Lisa\",\n" +
                "        \"age\": 23,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 12000\n" +
                "}";
        //when

        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(newEmployeeJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lisa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(12000));

        //then
        List<Employee> employees = employeeJpaRepository.findAll();
        assertThat(employees, hasSize(1));
        assertThat(employees.get(0).getName(), equalTo("Lisa"));
        assertThat(employees.get(0).getAge(), equalTo(23));
        assertThat(employees.get(0).getGender(), equalTo("Female"));
        assertThat(employees.get(0).getSalary(), equalTo(12000));
    }

    @Test
    void should_a_FeMale_employee_when_perform_get_given_employees() throws Exception {
        //given
        employeeJpaRepository.save(new Employee(1,"Sally", 22, "Female", 10000,preparedCompany.getId()));
        employeeJpaRepository.save(new Employee(2,"Tom", 33, "Male", 20000,preparedCompany.getId()));
        employeeJpaRepository.save(new Employee(3,"Lisa", 33, "Female", 30000,preparedCompany.getId()));


        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                .param("gender","Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000));


        //then

    }

    @Test
    void should_get_employee_by_id_1_when_perform_get_given_employees() throws Exception {
        //given
        Employee employee1 = new Employee(1,"Sally", 22, "Female", 10000,preparedCompany.getId());
        Employee employee = employeeJpaRepository.save(employee1);
        Employee employee2 = new Employee(2,"Tom", 33, "Male", 20000,preparedCompany.getId());
        employeeJpaRepository.save(employee2);

        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", employee.getId()))     //request

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"));

        //then
    }

    @Test
    void should_get_employees_when_perform_get_given_page_pageSize() throws Exception {
        //given
        employeeJpaRepository.save(new Employee(1,"Sally", 22, "Female", 10000,preparedCompany.getId()));
        employeeJpaRepository.save(new Employee(2,"Tom", 33, "Male", 20000,preparedCompany.getId()));

        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page","0")
                        .param("pageSize", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000));

        //then

    }

    @Test
    void should_update_employee_when_perform_get_given_employees() throws Exception {
        //given
        Employee employee = employeeJpaRepository.save(new Employee(1,"Sally", 22, "Female", 10000,preparedCompany.getId()));
        employeeJpaRepository.save(new Employee(2,"Tom", 33, "Male", 20000,preparedCompany.getId()));
        String newEmployeeJson = "{\n" +
                "        \"name\": \"Lisa\",\n" +
                "        \"age\": 23,\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"salary\": 12000\n" +
                "}";
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", employee.getId())     //request
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(12000));

        //then
        List<Employee> employees = employeeJpaRepository.findAll();
        assertThat(employees, hasSize(2));
        assertThat(employees.get(0).getName(), equalTo("Sally"));
        assertThat(employees.get(0).getAge(), equalTo(23));
        assertThat(employees.get(0).getGender(), equalTo("Female"));
        assertThat(employees.get(0).getSalary(), equalTo(12000));

    }


    @Test
    void should_delete_employee_by_id_1_when_perform_get_given_employees() throws Exception {
        //given
        Employee employee = employeeJpaRepository.save(new Employee(1,"Sally", 22, "Female", 10000,preparedCompany.getId()));
        employeeJpaRepository.save(new Employee(2,"Tom", 33, "Male", 20000,preparedCompany.getId()));

        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", employee.getId()))     //request

                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //then
        List<Employee> employees = employeeJpaRepository.findAll();
        assertThat(employees, hasSize(1));

    }



}
