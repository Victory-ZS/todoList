package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

//    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
//        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

//    GET       /employees                    # get employee list
    @GetMapping
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

//    GET       /employees/1                  # get a specific employee by ID
    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id){
        return employeeService.findById(id);
    }

//    GET       /employees?gender=male        # get all male employees
    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender){
        return employeeService.findByGender(gender);
    }

//    GET       /employees?page=1&pageSize=5  # Page query, page equals 1, pageSize equals 5
    @GetMapping(params = {"page","pageSize"})
    public List<Employee> findByPage(@RequestParam int page, @RequestParam int pageSize){
        return employeeService.findByPage(page, pageSize);
    }

//    POST      /employees                    # add an employee
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employee){
        return employeeService.insert(employee);
    }

//    PUT       /employees/1                  # update an employee
    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employeeToUpdate){
        return employeeService.update(id, employeeToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        Employee employee = employeeService.findById(id);
        employeeService.delete(employee.getId());
    }

}
