package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }
    //get all employee
    @GetMapping("/employees")
    public List<Employee> allEmployee(){
        return repository.findAll();
    }

    //create employee
    @PostMapping("/employees")
    public Employee create(@RequestBody Employee employee){
        return repository.save(employee);
    }

    //get employee by Id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
      Employee employee =  repository.findById(id).
              orElseThrow(()-> new ResourceNotFoundException("Employee is not exist with id"));
      return ResponseEntity.ok(employee);
    }

    //update
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id,@RequestBody Employee employeeDetails){
       Employee employee = repository.findById(id).
               orElseThrow(()-> new ResourceNotFoundException("Entity is not exist with id"));

       employee.setFirstName(employeeDetails.getFirstName());
       employee.setLastName(employeeDetails.getLastName());
       employee.setEmail(employeeDetails.getEmail());

       Employee updateEmployee = repository.save(employee);
       return ResponseEntity.ok(updateEmployee);
    }

    //delete Employee
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable int id){
        Employee employee = repository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Entity is not exist with id"));

        repository.delete(employee);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
