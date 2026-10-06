package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.Subject;
import com.sams.entity.Department;
import com.sams.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

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
    public ResponseEntity<ApiResponse> getAllSubjects(@RequestParam(required = false) Long departmentId) {
        if (departmentId != null) {
            return ResponseEntity.ok(ApiResponse.success("Subjects retrieved", subjectService.getSubjectsByDepartment(departmentId)));
        }
        return ResponseEntity.ok(ApiResponse.success("Subjects retrieved", subjectService.getAllSubjects()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Subject found", subjectService.getSubjectById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createSubject(@RequestBody Map<String, Object> body) {
        Subject subject = new Subject();
        subject.setSubjectCode((String) body.get("subjectCode"));
        subject.setName((String) body.get("name"));
        subject.setCredits(toInt(body.get("credits"), 3));
        subject.setSemester(toInt(body.get("semester"), 1));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            subject.setDepartment(new Department());
            subject.getDepartment().setId(deptId);
        }

        return ResponseEntity.ok(ApiResponse.success("Subject created", subjectService.createSubject(subject)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSubject(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Subject subject = new Subject();
        subject.setSubjectCode((String) body.get("subjectCode"));
        subject.setName((String) body.get("name"));
        subject.setCredits(toInt(body.get("credits"), 3));
        subject.setSemester(toInt(body.get("semester"), 1));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            subject.setDepartment(new Department());
            subject.getDepartment().setId(deptId);
        }

        return ResponseEntity.ok(ApiResponse.success("Subject updated", subjectService.updateSubject(id, subject)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok(ApiResponse.success("Subject deleted"));
    }
}
