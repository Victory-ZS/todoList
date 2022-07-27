package com.rest.springbootemployee.service;

import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;

import java.util.List;

public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee update(int id, Employee employeeToUpdateRequest) {

        Employee employee = employeeRepository.findById(id);
        if (employeeToUpdateRequest.getAge() != null){
            employee.setAge(employeeToUpdateRequest.getAge());
        }
        if (employeeToUpdateRequest.getSalary() != null){
            employee.setSalary(employeeToUpdateRequest.getSalary());
        }
        return employee;

    }

    public Employee insert(Employee newEmployee) {
        return employeeRepository.insert(newEmployee);
    }

    public void delete(int id) {
        employeeRepository.delete(employeeRepository.findById(id));
    }
}
