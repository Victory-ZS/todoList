package com.rest.springbootemployee.service;

import com.rest.springbootemployee.exception.EmployeeNotFoundException;
import com.rest.springbootemployee.repository.EmployeeJpaRepository;
import com.rest.springbootemployee.repository.InMemoryEmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {

    private final InMemoryEmployeeRepository employeeRepository;
    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeService(InMemoryEmployeeRepository employeeRepository, EmployeeJpaRepository employeeJpaRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeJpaRepository = employeeJpaRepository;
    }

    public List<Employee> findAll() {
        return employeeJpaRepository.findAll();
    }

    public Employee update(Integer id, Employee employeeToUpdateRequest) {

        Employee employee = this.findById(id);
        if (employeeToUpdateRequest.getAge() != null){
            employee.setAge(employeeToUpdateRequest.getAge());
        }
        if (employeeToUpdateRequest.getSalary() != null){
            employee.setSalary(employeeToUpdateRequest.getSalary());
        }

        employeeJpaRepository.save(employee);
        return employee;
    }

    public Employee insert(Employee newEmployee) {
        return employeeJpaRepository.save(newEmployee);
    }

    public void delete(Integer id) {
        employeeJpaRepository.deleteById(id);
    }

    public Employee findById(Integer id) {
        return employeeJpaRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeJpaRepository.findByGender(gender);
    }

    public Page<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeJpaRepository.findAll(PageRequest.of(page, pageSize));
    }
}
