package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.Department;
import com.sams.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllDepartments() {
        return ResponseEntity.ok(ApiResponse.success("Departments retrieved", departmentService.getAllDepartments()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Department found", departmentService.getDepartmentById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(ApiResponse.success("Department created", departmentService.createDepartment(department)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        return ResponseEntity.ok(ApiResponse.success("Department updated", departmentService.updateDepartment(id, department)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(ApiResponse.success("Department deleted"));
    }
}
