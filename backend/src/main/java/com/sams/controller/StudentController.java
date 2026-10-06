package com.sams.controller;

import com.sams.dto.ApiResponse;
import com.sams.entity.Student;
import com.sams.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class StudentController {

    @Autowired
    private StudentService studentService;

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
    public ResponseEntity<ApiResponse> getAllStudents(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String search) {
        List<Student> students;
        if (search != null && !search.isEmpty()) {
            if (departmentId != null) {
                students = studentService.searchStudentsByDepartment(departmentId, search);
            } else {
                students = studentService.searchStudents(search);
            }
        } else if (departmentId != null && classId != null) {
            students = studentService.getAllStudents().stream()
                    .filter(s -> s.getDepartment() != null && s.getDepartment().getId().equals(departmentId) 
                              && s.getClassEntity() != null && s.getClassEntity().getId().equals(classId))
                    .toList();
        } else if (departmentId != null) {
            students = studentService.getStudentsByDepartment(departmentId);
        } else if (classId != null) {
            students = studentService.getStudentsByClass(classId);
        } else {
            students = studentService.getAllStudents();
        }
        return ResponseEntity.ok(ApiResponse.success("Students retrieved", students));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Student found", studentService.getStudentById(id)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getStudentByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Student found", studentService.getStudentByUserId(userId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createStudent(@RequestBody Map<String, Object> body) {
        Student student = new Student();
        student.setRegisterNumber((String) body.get("registerNumber"));
        student.setFullName((String) body.get("fullName"));
        student.setEmail((String) body.get("email"));
        student.setPhone((String) body.get("phone"));

        Object dob = body.get("dateOfBirth");
        if (dob != null && !dob.toString().trim().isEmpty()) {
            student.setDateOfBirth(LocalDate.parse(dob.toString().trim()));
        }
        Object gender = body.get("gender");
        if (gender != null && !gender.toString().trim().isEmpty()) {
            student.setGender(Student.Gender.valueOf(gender.toString().trim()));
        }
        student.setAddress((String) body.get("address"));
        student.setYear(toInt(body.get("year"), 1));
        student.setSemester(toInt(body.get("semester"), 1));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            student.setDepartment(new com.sams.entity.Department());
            student.getDepartment().setId(deptId);
        }

        Long classId = toLong(body.get("classId"));
        if (classId != null) {
            student.setClassEntity(new com.sams.entity.ClassEntity());
            student.getClassEntity().setId(classId);
        }

        String password = body.get("password") != null && !body.get("password").toString().isEmpty()
                ? body.get("password").toString() : "Student@123";

        Student created = studentService.createStudent(student, password);
        return ResponseEntity.ok(ApiResponse.success("Student created successfully", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Student student = new Student();
        student.setRegisterNumber((String) body.get("registerNumber"));
        student.setFullName((String) body.get("fullName"));
        student.setEmail((String) body.get("email"));
        student.setPhone((String) body.get("phone"));

        Object dob = body.get("dateOfBirth");
        if (dob != null && !dob.toString().trim().isEmpty()) {
            student.setDateOfBirth(LocalDate.parse(dob.toString().trim()));
        }
        Object gender = body.get("gender");
        if (gender != null && !gender.toString().trim().isEmpty()) {
            student.setGender(Student.Gender.valueOf(gender.toString().trim()));
        }
        student.setAddress((String) body.get("address"));
        student.setYear(toInt(body.get("year"), 1));
        student.setSemester(toInt(body.get("semester"), 1));

        Long deptId = toLong(body.get("departmentId"));
        if (deptId != null) {
            student.setDepartment(new com.sams.entity.Department());
            student.getDepartment().setId(deptId);
        }
        Long classId = toLong(body.get("classId"));
        if (classId != null) {
            student.setClassEntity(new com.sams.entity.ClassEntity());
            student.getClassEntity().setId(classId);
        }

        Student updated = studentService.updateStudent(id, student);
        return ResponseEntity.ok(ApiResponse.success("Student updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deactivated successfully"));
    }
}
