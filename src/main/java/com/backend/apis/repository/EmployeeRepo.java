package com.backend.apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.apis.models.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

}
