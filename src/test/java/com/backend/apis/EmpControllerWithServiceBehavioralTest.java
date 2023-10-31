package com.backend.apis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.backend.apis.models.Employee;
import com.backend.apis.repository.EmployeeRepo;
import com.backend.apis.service.EmployeeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class EmpControllerWithServiceBehavioralTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepository;

    @BeforeEach
    public void setUp() {
        // Insert test data into the H2 in-memory database
        employeeRepository.save(new Employee(1L, "John", "Doe", "john@example.com", "9999999999", "123 Main St"));
        employeeRepository.save(new Employee(2L, "Jane", "Smith", "jane@example.com", "9713984103", "243 Brown St"));
        employeeRepository.save(new Employee(3L, "Bob", "Johnson", "bob@example.com", "9883848839", "123 Kale Ln"));
    }
    
//    @AfterEach
//    public void tearDown() {
//    	//Cleaning the database After Each Test.
//    	employeeRepository.deleteAll();
//    }

    @Test
    @Order(1)
    public void testGetEmployeeById() {
        // Given: A list of employees in the H2 database

        // When: A user chooses an employee Id
        long employeeId = 2L;

        // Simulate user action
        Employee emp = employeeService.getEmployeeById(employeeId);

        // Then: Verify the retrieved employee matches the expected values
        assertNotNull(emp, "Employee should be found in the database");
        assertEquals("Jane", emp.getFirstname(), "First name should match");
        assertEquals("Smith", emp.getLastname(), "Last name should match");
        assertEquals("jane@example.com", emp.getEmail(), "Email should match");
    }

    @Test
    @Order(2)
    public void testUpdateEmployeeBehavior() {
        // Given: A list of employees in the H2 database

        // When: A user selects an employee for updating
        long employeeIdToUpdate = 2L;
        Employee updatedEmployee = new Employee(employeeIdToUpdate, "Updated", "Employee", "updated@example.com", "111111111", "456 Elm St");

        // Simulate user action
        Employee emp = employeeService.updateEmployee(employeeIdToUpdate, updatedEmployee);

        // Then: Verify the updated employee matches the expected values
        assertNotNull(emp, "An updated employee should be returned");
        assertEquals("Updated", emp.getFirstname(), "Updated first name should match");

        // Verify that the updated employee exists in the database
        Employee updatedEmployeeFromDatabase = employeeRepository.findById(employeeIdToUpdate).orElse(null);
        assertNotNull(updatedEmployeeFromDatabase, "Updated employee should be in the database");
        assertEquals("Updated", updatedEmployeeFromDatabase.getFirstname(), "Updated first name should match");
        assertEquals("Employee", updatedEmployeeFromDatabase.getLastname(), "Updated last name should match");
        assertEquals("updated@example.com", updatedEmployeeFromDatabase.getEmail(), "Updated email should match");
    }

    @Test
    @Order(3)
    public void testCreateEmployeeBehavior() {
        // Given: A list of employees in the H2 database

        // When: A user creates a new employee
        Employee newEmployee = new Employee(4L, "New", "Employee", "new@example.com", "1234567890", "789 Elm St");

        // Simulate user action
        Employee responseEmp = employeeService.saveEmployee(newEmployee);

        // Then: Verify the created employee exists in the database
        assertNotNull(responseEmp, "A created employee should be returned");

        Employee createdEmployeeFromDatabase = employeeRepository.findById(newEmployee.getId()).orElse(null);
        assertNotNull(createdEmployeeFromDatabase, "Created employee should be in the database");
        assertEquals("New", createdEmployeeFromDatabase.getFirstname(), "Created first name should match");
        assertEquals("Employee", createdEmployeeFromDatabase.getLastname(), "Created last name should match");
        assertEquals("new@example.com", createdEmployeeFromDatabase.getEmail(), "Created email should match");
    }

    @Test
    @Order(4)
    public void testDeleteEmployeeBehavior() {
        // Given: A list of employees in the H2 database

        // When: A user deletes an employee
        long employeeIdToDelete = 2L;

        // Simulate user action
        employeeService.deleteEmployee(employeeIdToDelete);

        // Then: Verify the deleted employee does not exist in the database
        assertFalse(employeeRepository.existsById(employeeIdToDelete));
    }
}

