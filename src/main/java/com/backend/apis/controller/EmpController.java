package com.backend.apis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.apis.models.Employee;
import com.backend.apis.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmpController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee saveEmployee(@RequestBody Employee emp) {
        return employeeService.saveEmployee(emp);
    }

    @GetMapping("/employees/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/employees/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee empDetails) {
        Employee updatedEmp = employeeService.updateEmployee(id, empDetails);
        return ResponseEntity.ok(updatedEmp);
    }

    @DeleteMapping("/employees/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, String> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        Map<String, String> res = new HashMap<>();
        res.put("id", String.valueOf(id));
        res.put("message", "employee deleted successfully");
        return res;
    }
}
