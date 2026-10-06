package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.Faculty;
import com.sams.entity.Department;
import com.sams.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    private Long toLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).longValue();
        String str = obj.toString().trim();
        return str.isEmpty() ? null : Long.valueOf(str);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllFaculty(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) String search) {
        List<Faculty> faculty;
        if (search != null && !search.isEmpty()) {
            faculty = facultyService.searchFaculty(search);
        } else if (departmentId != null) {
            faculty = facultyService.getFacultyByDepartment(departmentId);
        } else {
            faculty = facultyService.getAllFaculty();
        }
        return ResponseEntity.ok(ApiResponse.success("Faculty retrieved", faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Faculty found", facultyService.getFacultyById(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getFacultyByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Faculty found", facultyService.getFacultyByUserId(userId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createFaculty(@RequestBody Map<String, Object> body) {
        Faculty faculty = new Faculty();
        faculty.setEmployeeId((String) body.get("employeeId"));
        faculty.setFullName((String) body.get("fullName"));
        faculty.setEmail((String) body.get("email"));
        faculty.setPhone((String) body.get("phone"));
        faculty.setQualification((String) body.get("qualification"));
        faculty.setDesignation((String) body.get("designation"));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            faculty.setDepartment(new Department());
            faculty.getDepartment().setId(deptId);
        }

        String password = body.get("password") != null && !body.get("password").toString().isEmpty()
                ? body.get("password").toString() : "Faculty@123";

        Faculty created = facultyService.createFaculty(faculty, password);
        return ResponseEntity.ok(ApiResponse.success("Faculty created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateFaculty(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Faculty faculty = new Faculty();
        faculty.setEmployeeId((String) body.get("employeeId"));
        faculty.setFullName((String) body.get("fullName"));
        faculty.setEmail((String) body.get("email"));
        faculty.setPhone((String) body.get("phone"));
        faculty.setQualification((String) body.get("qualification"));
        faculty.setDesignation((String) body.get("designation"));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            faculty.setDepartment(new Department());
            faculty.getDepartment().setId(deptId);
        }

        Faculty updated = facultyService.updateFaculty(id, faculty);
        return ResponseEntity.ok(ApiResponse.success("Faculty updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok(ApiResponse.success("Faculty deactivated successfully"));
    }
}
