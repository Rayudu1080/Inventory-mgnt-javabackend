package com.backend.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.apis.models.Employee;
import com.backend.apis.repository.EmployeeRepo;

@RestController
@RequestMapping("/api/v1")
public class EmpController {
	
	@Autowired
	private EmployeeRepo empRepo;
	
	@GetMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Employee> getAllEmployees(){
		return empRepo.findAll();
	}
	
	@PostMapping("/employees/create")
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee saveEmployee(@RequestBody Employee emp) {
		return empRepo.save(emp);
	}
}
