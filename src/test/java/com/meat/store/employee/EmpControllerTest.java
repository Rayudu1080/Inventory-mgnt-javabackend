package com.meat.store.employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.backend.apis.controller.EmpController;
import com.backend.apis.exceptions.ResourceNotFoundException;
import com.backend.apis.models.Employee;
import com.backend.apis.service.EmployeeService;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class EmpControllerTest {

    @InjectMocks
    private EmpController empController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = List.of(
            new Employee(1L, "John", "Doe", "john@example.com","9877376377", "1233 Debrown Ln, Tx 45677"),
            new Employee(2L, "Jane", "Smith", "jane@example.com","6924799923", "867 Lousi Ln, Tx 45677")
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        List<Employee> result = empController.getAllEmployees();
        assertEquals(2, result.size());
    }

    @Test
    public void testSaveEmployee() {
        Employee employeeToSave = new Employee(3L, "New", "Employee", "new@example.com","6924799923", "867 Lousi Ln, Tx 45677");
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employeeToSave);

        Employee savedEmployee = empController.saveEmployee(employeeToSave);
        assertNotNull(savedEmployee);
        assertEquals("New", savedEmployee.getFirstname());
        assertEquals("Employee", savedEmployee.getLastname());
        assertEquals("new@example.com", savedEmployee.getEmail());
    }

    @Test
    public void testGetEmployeeById() {
        long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", "john@example.com","6924799923", "1233 Uptown Ln, Tx 45677");
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        Employee result = empController.getEmployeeById(employeeId);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void testUpdateEmployee() {
        long employeeId = 1L;
        Employee updatedEmployee = new Employee(employeeId, "Updated", "Employee", "updated@example.com","6924799923", "1233 Uptown Ln, Tx 45677");

        // Mock the void method updateEmployee to do nothing
        doNothing().when(employeeService).updateEmployee(eq(employeeId), any(Employee.class));

        ResponseEntity<Employee> responseEntity = empController.updateEmployee(employeeId, updatedEmployee);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Updated", responseEntity.getBody().getFirstname());
        assertEquals("Employee", responseEntity.getBody().getLastname());
        assertEquals("updated@example.com", responseEntity.getBody().getEmail());
    }

    @Test
    public void testDeleteEmployee() {
        long employeeId = 1L;
        doNothing().when(employeeService).deleteEmployee(employeeId);

        Map<String, String> result = empController.deleteEmployee(employeeId);
        assertEquals(1, result.size());
        assertEquals("1", result.get("id"));
        assertEquals("employee deleted successfully", result.get("message"));
    }

    @Test
    public void testDeleteEmployeeNotFound() {
        long employeeId = 1L;
        doThrow(new ResourceNotFoundException("Employee Does Not Exist with id: " + employeeId))
                .when(employeeService).deleteEmployee(employeeId);

        assertThrows(ResourceNotFoundException.class, () -> empController.deleteEmployee(employeeId));
    }
}