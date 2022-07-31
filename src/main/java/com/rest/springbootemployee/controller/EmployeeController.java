package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.controller.dto.EmployeeRequest;
import com.rest.springbootemployee.controller.dto.EmployeeResponse;
import com.rest.springbootemployee.controller.mapper.EmployeeMapper;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

//    public EmployeeController(EmployeeService employeeService){
//        this.employeeService = employeeService;
//    }

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> findAll(){
        return employeeService.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponse findById(@PathVariable int id){
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> findByGender(@RequestParam String gender){
        return employeeService.findByGender(gender)
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page","pageSize"})
    public List<EmployeeResponse> findByPage(@RequestParam int page, @RequestParam int pageSize){
        return employeeService.findByPage(page, pageSize)
                .stream()
                .map(employeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest){
        Employee createdEmployee = employeeService.insert(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(createdEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable int id, @RequestBody EmployeeRequest employeeRequest){
        return employeeMapper.toResponse(employeeService.update(id, employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        Employee employee = employeeService.findById(id);
        employeeService.delete(employee.getId());
    }

}
