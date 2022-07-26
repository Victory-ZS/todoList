package com.rest.springbootemployee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees;
    public EmployeeRepository(){
        employees = new ArrayList<>();
        employees.add(new Employee(1,"Lily1",23,"male",1000));
        employees.add(new Employee(2,"Lily2",23,"female",1000));
        employees.add(new Employee(3,"Lily3",23,"male",1000));
        employees.add(new Employee(4,"Lily4",23,"female",1000));
    }

    public List<Employee> findAll(){
        return employees;
    }

    public Employee findById(int id){
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender){
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

}
