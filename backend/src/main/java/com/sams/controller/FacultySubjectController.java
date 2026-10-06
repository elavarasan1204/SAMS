package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.*;
import com.sams.service.FacultySubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/faculty-subjects")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class FacultySubjectController {

    @Autowired
    private FacultySubjectService facultySubjectService;

    private Long toLong(Object obj) {
        if (obj == null) return null;
        if (obj instanceof Number) return ((Number) obj).longValue();
        String str = obj.toString().trim();
        return str.isEmpty() ? null : Long.valueOf(str);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllAssignments(
            @RequestParam(required = false) Long facultyId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long classId) {
        if (facultyId != null) {
            return ResponseEntity.ok(ApiResponse.success("Assignments retrieved",
                    facultySubjectService.getAssignmentsByFaculty(facultyId)));
        }
        if (subjectId != null) {
            return ResponseEntity.ok(ApiResponse.success("Assignments retrieved",
                    facultySubjectService.getAssignmentsBySubject(subjectId)));
        }
        if (classId != null) {
            return ResponseEntity.ok(ApiResponse.success("Assignments retrieved",
                    facultySubjectService.getAssignmentsByClass(classId)));
        }
        return ResponseEntity.ok(ApiResponse.success("Assignments retrieved",
                facultySubjectService.getAllAssignments()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAssignment(@RequestBody Map<String, Object> body) {
        FacultySubject fs = new FacultySubject();

        Long fId = toLong(body.get("facultyId"));
        if (fId != null) {
            fs.setFaculty(new Faculty());
            fs.getFaculty().setId(fId);
        }

        Long sId = toLong(body.get("subjectId"));
        if (sId != null) {
            fs.setSubject(new Subject());
            fs.getSubject().setId(sId);
        }

        Long cId = toLong(body.get("classId"));
        if (cId != null) {
            fs.setClassEntity(new ClassEntity());
            fs.getClassEntity().setId(cId);
        }

        fs.setAcademicYear(body.get("academicYear") != null ? body.get("academicYear").toString() : "2024-2025");
        return ResponseEntity.ok(ApiResponse.success("Assignment created", facultySubjectService.createAssignment(fs)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAssignment(@PathVariable Long id) {
        facultySubjectService.deleteAssignment(id);
        return ResponseEntity.ok(ApiResponse.success("Assignment deleted"));
    }
}
