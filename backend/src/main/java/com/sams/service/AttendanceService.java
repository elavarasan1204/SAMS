package com.sams.service;

import com.sams.dto.*;
import com.sams.entity.*;
import com.sams.exception.*;
import com.sams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultySubjectRepository facultySubjectRepository;

    @Transactional
    public List<Attendance> markAttendance(AttendanceRequest request, Long facultyUserId) {
        Faculty faculty = facultyRepository.findByUserId(facultyUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found."));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));

        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found."));

        // Verify faculty is assigned to this subject-class
        boolean isAssigned = !facultySubjectRepository.findByFacultyId(faculty.getId()).stream()
                .filter(fs -> fs.getSubject().getId().equals(request.getSubjectId()) &&
                        fs.getClassEntity().getId().equals(request.getClassId()))
                .toList().isEmpty();

        if (!isAssigned) {
            throw new IllegalArgumentException("You are not assigned to this subject/class combination.");
        }

        List<Attendance> savedAttendance = new ArrayList<>();

        for (AttendanceRequest.AttendanceEntry entry : request.getAttendanceList()) {
            Student student = studentRepository.findById(entry.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + entry.getStudentId()));

            // Check for existing attendance record
            Optional<Attendance> existingOpt = attendanceRepository
                    .findByStudentIdAndSubjectIdAndDate(entry.getStudentId(), request.getSubjectId(), request.getDate());

            Attendance attendance;
            if (existingOpt.isPresent()) {
                // Update existing record
                attendance = existingOpt.get();
                attendance.setStatus(Attendance.Status.valueOf(entry.getStatus()));
            } else {
                // Create new record
                attendance = Attendance.builder()
                        .student(student)
                        .subject(subject)
                        .classEntity(classEntity)
                        .faculty(faculty)
                        .date(request.getDate())
                        .status(Attendance.Status.valueOf(entry.getStatus()))
                        .build();
            }

            savedAttendance.add(attendanceRepository.save(attendance));
        }

        return savedAttendance;
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceBySubjectAndClassAndDate(Long subjectId, Long classId, LocalDate date) {
        return attendanceRepository.findBySubjectIdAndClassEntityIdAndDate(subjectId, classId, date);
    }

    public List<Attendance> getAttendanceByFaculty(Long facultyId) {
        return attendanceRepository.findByFacultyId(facultyId);
    }

    public Attendance updateAttendance(Long id, Attendance.Status status) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found."));
        attendance.setStatus(status);
        return attendanceRepository.save(attendance);
    }

    // ===== Attendance Calculation =====

    public AttendanceReportDTO getStudentSubjectAttendance(Long studentId, Long subjectId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));

        long total = attendanceRepository.countTotalClasses(studentId, subjectId);
        long present = attendanceRepository.countPresentClasses(studentId, subjectId);
        long absent = total - present;
        double percentage = total > 0 ? (double) present / total * 100 : 0;

        return AttendanceReportDTO.builder()
                .studentId(studentId)
                .studentName(student.getFullName())
                .registerNumber(student.getRegisterNumber())
                .className(student.getClassEntity().getName())
                .subjectId(subjectId)
                .subjectName(subject.getName())
                .subjectCode(subject.getSubjectCode())
                .totalClasses(total)
                .presentClasses(present)
                .absentClasses(absent)
                .percentage(Math.round(percentage * 100.0) / 100.0)
                .status(getAttendanceStatus(percentage))
                .build();
    }

    public List<AttendanceReportDTO> getStudentAllSubjectsAttendance(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        List<Long> subjectIds = attendanceRepository.findDistinctSubjectIdsByStudentId(studentId);

        return subjectIds.stream()
                .map(subjectId -> getStudentSubjectAttendance(studentId, subjectId))
                .collect(Collectors.toList());
    }

    public AttendanceReportDTO getStudentOverallAttendance(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found."));

        long total = attendanceRepository.countTotalByStudent(studentId);
        long present = attendanceRepository.countPresentByStudent(studentId);
        long absent = total - present;
        double percentage = total > 0 ? (double) present / total * 100 : 0;

        return AttendanceReportDTO.builder()
                .studentId(studentId)
                .studentName(student.getFullName())
                .registerNumber(student.getRegisterNumber())
                .className(student.getClassEntity().getName())
                .totalClasses(total)
                .presentClasses(present)
                .absentClasses(absent)
                .percentage(Math.round(percentage * 100.0) / 100.0)
                .status(getAttendanceStatus(percentage))
                .build();
    }

    public List<AttendanceReportDTO> getSubjectAttendanceReport(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));

        List<Attendance> records = attendanceRepository.findBySubjectId(subjectId);
        Map<Long, List<Attendance>> byStudent = records.stream()
                .collect(Collectors.groupingBy(a -> a.getStudent().getId()));

        return byStudent.entrySet().stream()
                .map(entry -> {
                    Student student = entry.getValue().get(0).getStudent();
                    long total = entry.getValue().size();
                    long present = entry.getValue().stream().filter(a -> a.getStatus() == Attendance.Status.PRESENT).count();
                    double pct = total > 0 ? (double) present / total * 100 : 0;
                    return AttendanceReportDTO.builder()
                            .studentId(student.getId())
                            .studentName(student.getFullName())
                            .registerNumber(student.getRegisterNumber())
                            .className(student.getClassEntity().getName())
                            .subjectId(subjectId)
                            .subjectName(subject.getName())
                            .subjectCode(subject.getSubjectCode())
                            .totalClasses(total)
                            .presentClasses(present)
                            .absentClasses(total - present)
                            .percentage(Math.round(pct * 100.0) / 100.0)
                            .status(getAttendanceStatus(pct))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<AttendanceReportDTO> getLowAttendanceStudents(Long departmentId, double threshold) {
        List<Student> students;
        if (departmentId != null) {
            students = studentRepository.findByDepartmentId(departmentId);
        } else {
            students = studentRepository.findAll();
        }

        List<AttendanceReportDTO> lowAttendance = new ArrayList<>();

        for (Student student : students) {
            if (!student.getEnabled()) continue;
            long total = attendanceRepository.countTotalByStudent(student.getId());
            if (total == 0) continue;
            long present = attendanceRepository.countPresentByStudent(student.getId());
            double pct = (double) present / total * 100;
            if (pct < threshold) {
                lowAttendance.add(AttendanceReportDTO.builder()
                        .studentId(student.getId())
                        .studentName(student.getFullName())
                        .registerNumber(student.getRegisterNumber())
                        .className(student.getClassEntity().getName())
                        .totalClasses(total)
                        .presentClasses(present)
                        .absentClasses(total - present)
                        .percentage(Math.round(pct * 100.0) / 100.0)
                        .status(getAttendanceStatus(pct))
                        .build());
            }
        }

        return lowAttendance;
    }

    public List<Attendance> getAttendanceHistory(Long subjectId, Long classId) {
        if (subjectId != null && classId != null) {
            return attendanceRepository.findBySubjectIdAndClassEntityIdAndDate(subjectId, classId, null) != null ?
                    attendanceRepository.findAll().stream()
                            .filter(a -> a.getSubject().getId().equals(subjectId) && a.getClassEntity().getId().equals(classId))
                            .collect(Collectors.toList()) :
                    new ArrayList<>();
        }
        return attendanceRepository.findAll();
    }

    public List<LocalDate> getAttendanceDates(Long subjectId, Long classId) {
        return attendanceRepository.findDistinctDatesBySubjectAndClass(subjectId, classId);
    }

    public List<Attendance> getAttendanceByDepartment(Long deptId) {
        return attendanceRepository.findByDepartmentId(deptId);
    }

    private String getAttendanceStatus(double percentage) {
        if (percentage >= 85) return "GOOD";
        if (percentage >= 75) return "WARNING";
        return "CRITICAL";
    }
}
