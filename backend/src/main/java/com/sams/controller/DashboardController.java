package com.sams.controller;

import com.sams.dto.*;
import com.sams.entity.*;
import com.sams.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class DashboardController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private HodService hodService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClassService classService;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse> getDashboardStats() {
        DashboardStats stats = DashboardStats.builder()
                .totalStudents(studentService.countStudents())
                .totalFaculty(facultyService.countFaculty())
                .totalHods(hodService.countHods())
                .totalDepartments(departmentService.countDepartments())
                .totalSubjects(subjectService.countSubjects())
                .totalClasses(classService.countClasses())
                .build();
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats", stats));
    }
}
