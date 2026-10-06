package com.sams.controller;

import com.sams.dto.*;
import com.sams.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ReportController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse> getStudentReport(@PathVariable Long studentId) {
        List<AttendanceReportDTO> report = attendanceService.getStudentAllSubjectsAttendance(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student attendance report", report));
    }

    @GetMapping("/student/{studentId}/overall")
    public ResponseEntity<ApiResponse> getStudentOverallReport(@PathVariable Long studentId) {
        AttendanceReportDTO report = attendanceService.getStudentOverallAttendance(studentId);
        return ResponseEntity.ok(ApiResponse.success("Student overall attendance", report));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<ApiResponse> getSubjectReport(@PathVariable Long subjectId) {
        List<AttendanceReportDTO> report = attendanceService.getSubjectAttendanceReport(subjectId);
        return ResponseEntity.ok(ApiResponse.success("Subject attendance report", report));
    }

    @GetMapping("/low-attendance")
    public ResponseEntity<ApiResponse> getLowAttendance(
            @RequestParam(required = false) Long departmentId,
            @RequestParam(defaultValue = "75") double threshold) {
        List<AttendanceReportDTO> report = attendanceService.getLowAttendanceStudents(departmentId, threshold);
        return ResponseEntity.ok(ApiResponse.success("Low attendance students", report));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse> getDepartmentReport(@PathVariable Long departmentId) {
        return ResponseEntity.ok(ApiResponse.success("Department attendance",
                attendanceService.getAttendanceByDepartment(departmentId)));
    }
}
