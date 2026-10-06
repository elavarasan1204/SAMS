package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.ClassEntity;
import com.sams.entity.Department;
import com.sams.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ClassController {

    @Autowired
    private ClassService classService;

    private Long toLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).longValue();
        String str = obj.toString().trim();
        return str.isEmpty() ? null : Long.valueOf(str);
    }

    private Integer toInt(Object obj, int defaultVal) {
        if (obj == null) return defaultVal;
        if (obj instanceof Number) return ((Number) obj).intValue();
        String str = obj.toString().trim();
        return str.isEmpty() ? defaultVal : Integer.valueOf(str);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllClasses(@RequestParam(required = false) Long departmentId) {
        if (departmentId != null) {
            return ResponseEntity.ok(ApiResponse.success("Classes retrieved", classService.getClassesByDepartment(departmentId)));
        }
        return ResponseEntity.ok(ApiResponse.success("Classes retrieved", classService.getAllClasses()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getClassById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Class found", classService.getClassById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createClass(@RequestBody Map<String, Object> body) {
        ClassEntity cls = new ClassEntity();
        cls.setName((String) body.get("name"));
        cls.setYear(toInt(body.get("year"), 1));
        cls.setSection((String) body.get("section"));
        cls.setSemester(toInt(body.get("semester"), 1));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            cls.setDepartment(new Department());
            cls.getDepartment().setId(deptId);
        }

        return ResponseEntity.ok(ApiResponse.success("Class created", classService.createClass(cls)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateClass(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        ClassEntity cls = new ClassEntity();
        cls.setName((String) body.get("name"));
        cls.setYear(toInt(body.get("year"), 1));
        cls.setSection((String) body.get("section"));
        cls.setSemester(toInt(body.get("semester"), 1));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            cls.setDepartment(new Department());
            cls.getDepartment().setId(deptId);
        }

        return ResponseEntity.ok(ApiResponse.success("Class updated", classService.updateClass(id, cls)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return ResponseEntity.ok(ApiResponse.success("Class deleted"));
    }
}
