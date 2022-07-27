package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        Employee employee = new Employee(2,"Tom", 23, "Male", 90000);
        given(employeeRepository.insert(employee)).willReturn(employee);
        //when
        Employee newEmployee = employeeService.insert(employee);
        //then
        assertEquals("Tom", newEmployee.getName());
        assertEquals(23, newEmployee.getAge());
        assertEquals("Male", newEmployee.getGender());
        assertEquals(90000, newEmployee.getSalary());

    }

    @Test
    void should_call_repository_delete_when_delete_given_by_id() {
        //given

        //when
        employeeService.delete(1);
        //then
        verify(employeeRepository,times(1)).delete(employeeRepository.findById(1));
    }

    @Test
    void should_a_employee_by_id_1_employee_when_get_given_employees() {
        //given
        Employee firstEmployee = new Employee(1,"Susan", 23, "Female", 9000);
        given(employeeRepository.findById(1)).willReturn(firstEmployee);

        //when
        Employee employee = employeeService.findById(1);

        //then
        assertEquals(firstEmployee, employee);
    }

    @Test
    void should_a_Female_employee_when_get_given_employees() {
        //given
        List<Employee> preparedEmployees = new ArrayList<>();
        Employee firstEmployee = new Employee(1,"Susan", 20, "Female", 9000);
//        Employee secondEmployee = new Employee(2,"Tom", 30, "Male", 9000);
        preparedEmployees.add(firstEmployee);
//        preparedEmployees.add(secondEmployee);
        given(employeeRepository.findByGender("Female")).willReturn(preparedEmployees);

        //when
        List<Employee>  femaleEmployees = employeeService.findByGender("Female");

        //then
        assertEquals(1, femaleEmployees.size());
        assertEquals(firstEmployee, femaleEmployees.get(0));
    }




}
