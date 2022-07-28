package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyJpaRepository extends JpaRepository<Company, Integer> {

    List<Employee> findEmployeesById(int id);
}
