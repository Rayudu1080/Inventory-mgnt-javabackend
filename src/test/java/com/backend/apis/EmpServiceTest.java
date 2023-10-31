package com.backend.apis;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.backend.apis.exceptions.ResourceNotFoundException;
import com.backend.apis.models.Employee;
import com.backend.apis.repository.EmployeeRepo;
import com.backend.apis.service.EmployeeService;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmpServiceTest {

	@InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepo empRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() {
        when(empRepo.findAll()).thenReturn(List.of(
            new Employee(1L, "John", "Doe", "john@example.com","9877376377", "1233 Debrown Ln, Tx 45677"),
            new Employee(2L, "Jane", "Smith", "jane@example.com","9877376377", "1233 Debrown Ln, Tx 45677")
        ));

        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
    }

    @Test
    public void testSaveEmployeeValid() {
        Employee employeeToSave = new Employee(3L, "New", "Employee", "new@example.com","999999999", "New Address");
        when(empRepo.save(employeeToSave)).thenReturn(employeeToSave);

        Employee savedEmployee = employeeService.saveEmployee(employeeToSave);
        assertNotNull(savedEmployee);
        assertEquals("New", savedEmployee.getFirstname());
        assertEquals("Employee", savedEmployee.getLastname());
        assertEquals("new@example.com", savedEmployee.getEmail());
        assertEquals("999999999", savedEmployee.getPhone());
        assertEquals("New Address", savedEmployee.getAddress());
    }

    @Test
    public void testSaveEmployeeNull() {
        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(null));
    }

    @Test
    public void testSaveEmployeeEmptyFirstName() {
        Employee employeeToSave = new Employee(3L, "", "Employee", "new@example.com","6924799923", "1233 Debrown Ln, Tx 45677");
        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(employeeToSave));
    }

    @Test
    public void testGetEmployeeById() {
        long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", "Doe", "john@example.com","6924799923", "867 Lousi Ln, Tx 45677");
        when(empRepo.findById(employeeId)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(employeeId);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        long employeeId = 1L;
        when(empRepo.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    @Test
    public void testUpdateEmployeeValid() {
        long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "John", "Doe", "john@example.com","6924799923", "1233 Uptown Ln, Tx 45677");
        Employee updatedEmployee = new Employee(employeeId, "Updated", "Employee", "updated@example.com","3456776522", "133 Debre Ln, Tx 45677");
        when(empRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(empRepo.save(existingEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(employeeId, updatedEmployee);
        assertEquals("Updated", result.getFirstname());
        assertEquals("Employee", result.getLastname());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    public void testUpdateEmployeeNullDetails() {
        long employeeId = 1L;
        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(employeeId, null));
    }

    @Test
    public void testUpdateEmployeeEmptyFirstName() {
        long employeeId = 1L;
        Employee updatedEmployee = new Employee(employeeId, "", "Employee", "updated@example.com","8658656662", "288 Debrown Ln, Tx 45677");
        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(employeeId, updatedEmployee));
    }

    @Test
    public void testDeleteEmployee() {
        long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "John", "Doe", "john@example.com","8778828737", "909 Peacock Ln, Tx 45677");
        when(empRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        assertDoesNotThrow(() -> employeeService.deleteEmployee(employeeId));
    }

    @Test
    public void testDeleteEmployeeNotFound() {
        long employeeId = 1L;
        when(empRepo.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));
    }
    
    @Test
    public void testUpdateEmployeeEmptyLastName() {
        long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "John", "Doe", "john@example.com","6924799923", "1233 Uptown Ln, Tx 45677");
        Employee updatedEmployee = new Employee(employeeId, "Updated", "", "updated@example.com","3456776522", "133 Debre Ln, Tx 45677");
        
        when(empRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(employeeId, updatedEmployee));
    }

    @Test
    public void testUpdateEmployeeEmptyEmail() {
        long employeeId = 1L;Employee existingEmployee = new Employee(employeeId, "John", "Doe", "john@example.com","6924799923", "1233 Uptown Ln, Tx 45677");
        Employee updatedEmployee = new Employee(employeeId, "Updated", "Employee", "","3456776522", "133 Debre Ln, Tx 45677");
        
        when(empRepo.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        assertThrows(IllegalArgumentException.class, () -> employeeService.updateEmployee(employeeId, updatedEmployee));
    }

    @Test
    public void testSaveEmployeeEmptyLastName() {
        Employee employeeToSave = new Employee(3L, "New", "", "new@example.com","3456776522", "133 Debre Ln, Tx 45677");
        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(employeeToSave));
    }

    @Test
    public void testSaveEmployeeEmptyEmail() {
        Employee employeeToSave = new Employee(3L, "New", "Employee", "","3456776522", "133 Debre Ln, Tx 45677");
        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(employeeToSave));
    }

    @Test
    public void testSaveEmployeeEmptyPhone() {
        Employee employeeToSave = new Employee(3L, "New", "Employee", "new@example.com","", "133 Debre Ln, Tx 45677");
        employeeToSave.setPhone("");
        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(employeeToSave));
    }
    

}