package com.rest.springbootemployee;

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
public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;


    @Test
    void should_return_all_when_find_all_given_employees() {
        //given
        List<Employee> preparedEmployees = new ArrayList<>();
        Employee firstEmployee = new Employee(1,"Susan", 23, "Female", 9000);
        Employee secondEmployee = new Employee(2,"Tom", 23, "Female", 9000);
        preparedEmployees.add(firstEmployee);
        preparedEmployees.add(secondEmployee);
        given(employeeRepository.findAll()).willReturn(preparedEmployees);

        //when
        List<Employee>  employees = employeeService.findAll();

        //then
        assertEquals(2, employees.size());
        assertEquals(firstEmployee, employees.get(0));

    }

    @Test
    void should_update_only_age_and_salary_when_update_given_employee() {
        //given
        Employee employeeToUpdate = new Employee(1,"Susan", 20, "Female", 9000);
        Employee employeeToUpdateRequest = new Employee(1,"Susan", 40, "Female", 90000);
        given(employeeRepository.findById(1)).willReturn(employeeToUpdate);
        //when
        employeeService.update(1,employeeToUpdateRequest);

        //then
        assertEquals("Susan",employeeToUpdate.getName());
        assertEquals(40,employeeToUpdate.getAge());
        assertEquals("Female",employeeToUpdate.getGender());
        assertEquals(90000,employeeToUpdate.getSalary());

    }

    @Test
    void should_create_employee_when_post_given_a_new_employee() {
        //given
        Employee newEmployee = new Employee(2,"Tom", 23, "Male", 90000);
        given(employeeRepository.insert(newEmployee)).willReturn(newEmployee);
        //when
        Employee employeeAdd = employeeService.insert(newEmployee);
        //then
        assertEquals("Tom", employeeAdd.getName());
        assertEquals(23, employeeAdd.getAge());
        assertEquals("Male", employeeAdd.getGender());
        assertEquals(90000, employeeAdd.getSalary());

    }



}
