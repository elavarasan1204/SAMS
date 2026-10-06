package com.sams.controller;

import com.sams.dto.*;
import com.sams.entity.*;
import com.sams.repository.UserRepository;
import com.sams.security.JwtUtil;
import com.sams.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> markAttendance(
            @RequestBody AttendanceRequest request,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        List<Attendance> saved = attendanceService.markAttendance(request, userId);
        return ResponseEntity.ok(ApiResponse.success("Attendance marked successfully", saved));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAttendance(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Long facultyId) {
        if (subjectId != null && classId != null && date != null) {
            LocalDate localDate = LocalDate.parse(date);
            return ResponseEntity.ok(ApiResponse.success("Attendance retrieved",
                    attendanceService.getAttendanceBySubjectAndClassAndDate(subjectId, classId, localDate)));
        }
        if (studentId != null) {
            return ResponseEntity.ok(ApiResponse.success("Attendance retrieved",
                    attendanceService.getAttendanceByStudent(studentId)));
        }
        if (facultyId != null) {
            return ResponseEntity.ok(ApiResponse.success("Attendance retrieved",
                    attendanceService.getAttendanceByFaculty(facultyId)));
        }
        return ResponseEntity.ok(ApiResponse.success("Attendance retrieved",
                attendanceService.getAttendanceByStudent(0L)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateAttendance(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        Attendance.Status status = Attendance.Status.valueOf(body.get("status"));
        return ResponseEntity.ok(ApiResponse.success("Attendance updated",
                attendanceService.updateAttendance(id, status)));
    }

    @GetMapping("/dates")
    public ResponseEntity<ApiResponse> getAttendanceDates(
            @RequestParam Long subjectId, @RequestParam Long classId) {
        return ResponseEntity.ok(ApiResponse.success("Dates retrieved",
                attendanceService.getAttendanceDates(subjectId, classId)));
    }
}
