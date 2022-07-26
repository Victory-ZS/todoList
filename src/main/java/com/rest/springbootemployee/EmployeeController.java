package com.rest.springbootemployee;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeRepository employeeRepository;
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

//    GET       /employees                    # get employee list
    @GetMapping
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

//    GET       /employees/1                  # get a specific employee by ID
    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id){
        return employeeRepository.findById(id);
    }

//    GET       /employees?gender=male        # get all male employees
    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender){
        return employeeRepository.findByGender(gender);
    }

//    GET       /employees?page=1&pageSize=5  # Page query, page equals 1, pageSize equals 5
    @GetMapping(params = {"page","pageSize"})
    public List<Employee> findByPage(@RequestParam int page,int pageSize){
        return employeeRepository.findByPage(page, pageSize);
    }

//    POST      /employees                    # add an employee
    @PostMapping
    public Employee create(@RequestBody Employee employee){
        return employeeRepository.insert(employee);
    }

//    PUT       /employees/1                  # update an employee
    @PutMapping("/{id}")
    public Employee update(@PathVariable int id, @RequestBody Employee employeeToUpdate){
        Employee existingEmployee = employeeRepository.findById(id);
        if (employeeToUpdate.getAge() != null){
            existingEmployee.setAge(employeeToUpdate.getAge());
        }
        if (employeeToUpdate.getSalary() != null){
            existingEmployee.setSalary(employeeToUpdate.getSalary());
        }
        return existingEmployee;

    }


}
