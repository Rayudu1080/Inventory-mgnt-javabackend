package com.backend.apis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.apis.exceptions.ResourceNotFoundException;
import com.backend.apis.models.Employee;
import com.backend.apis.repository.EmployeeRepo;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo empRepo;

    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    public Employee saveEmployee(Employee emp) {
    	
    	// Check if the employee object is null
        if (emp == null) {
            throw new IllegalArgumentException("Employee object cannot be null");
        }

        // Check for null or empty first name
        if (emp.getFirstname() == null || emp.getFirstname().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        // Check for null or empty last name
        if (emp.getLastname() == null || emp.getLastname().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        

        // Check for null or empty email
        if (emp.getEmail() == null || emp.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone Number cannot be null or empty");
        }
        // Check for null or empty Phone
        if (emp.getPhone() == null || emp.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone Number cannot be null or empty");
        }

        // Check for null or empty Address
        if (emp.getAddress() == null || emp.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        

        return empRepo.save(emp);
    }

    public Employee getEmployeeById(long id) {
        return empRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee Not found with Id:" + id));
    }

    public Employee updateEmployee(long id, Employee empDetails) {
        
        // Check if the updated employee details object is null
        if (empDetails == null) {
            throw new IllegalArgumentException("Updated employee details cannot be null");
        }

     // Check for null or empty first name
        if (empDetails.getFirstname() == null || empDetails.getFirstname().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        // Check for null or empty last name
        if (empDetails.getLastname() == null || empDetails.getLastname().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        
        // Check for null or empty email
        if (empDetails.getEmail() == null || empDetails.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone Number cannot be null or empty");
        }
        // Check for null or empty Phone
        if (empDetails.getPhone() == null || empDetails.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone Number cannot be null or empty");
        }

        // Check for null or empty Address
        if (empDetails.getAddress() == null || empDetails.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        
        Employee employee = empRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not found with Id:" + id));
        
        employee.setFirstname(empDetails.getFirstname());
        employee.setLastname(empDetails.getLastname());
        employee.setEmail(empDetails.getEmail());
        employee.setPhone(empDetails.getPhone());
        employee.setAddress(empDetails.getAddress());
        return empRepo.save(employee);
    }

    public void deleteEmployee(long id) {
        Employee emp = empRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Does Not Exist with id: " + id));
        empRepo.delete(emp);
    }
}