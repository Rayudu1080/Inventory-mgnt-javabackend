package com.backend.apis.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.apis.exceptions.ResourceNotFoundException;
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
	
	@PostMapping("/employees")
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee saveEmployee(@RequestBody Employee emp) {
		return empRepo.save(emp);
	}
	
	@GetMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public Employee getEmployeeById(@PathVariable long id) {
		Employee emp = empRepo.findById(id)
				.orElseThrow(()-> 
				new ResourceNotFoundException("Employee Not found with Id:"+id));
		return emp;
	}
	
	@PutMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id,
			@RequestBody Employee empDetails){
		Employee employee = empRepo.findById(id).
				orElseThrow(()-> new ResourceNotFoundException("Employee Not found with Id:"+id));
		employee.setFirstname(empDetails.getFirstname());
		employee.setLastname(empDetails.getLastname());
		employee.setEmail(empDetails.getEmail());
		
		Employee updatedEmp = empRepo.save(employee);
		return ResponseEntity.ok(updatedEmp);
	}
	
	@DeleteMapping("/employees/{id}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> deleteEmployee(@PathVariable long id){
		Employee emp = empRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee Does Not Exist with id: "+id));
		
		empRepo.delete(emp);
		
		return ResponseEntity.ok("Employee with id:"+id+" is deleted");
	}
	
}
