package com.sams.service;

import com.sams.entity.*;
import com.sams.exception.*;
import com.sams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user id: " + userId));
    }

    public List<Student> getStudentsByDepartment(Long departmentId) {
        return studentRepository.findByDepartmentId(departmentId);
    }

    public List<Student> getStudentsByClass(Long classId) {
        return studentRepository.findByClassEntityId(classId);
    }

    public List<Student> searchStudents(String search) {
        return studentRepository.searchStudents(search);
    }

    public List<Student> searchStudentsByDepartment(Long deptId, String search) {
        return studentRepository.searchStudentsByDepartment(deptId, search);
    }

    @Transactional
    public Student createStudent(Student student, String password) {
        if (studentRepository.existsByRegisterNumber(student.getRegisterNumber())) {
            throw new DuplicateResourceException("Student with register number " + student.getRegisterNumber() + " already exists.");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists.");
        }

        Department dept = departmentRepository.findById(student.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
        ClassEntity cls = classRepository.findById(student.getClassEntity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found."));

        // Create user account
        String username = student.getRegisterNumber().toLowerCase();
        if (userRepository.existsByUsername(username)) {
            username = student.getEmail().split("@")[0];
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(student.getEmail())
                .fullName(student.getFullName())
                .role(User.Role.STUDENT)
                .enabled(true)
                .build();
        user = userRepository.save(user);

        student.setUser(user);
        student.setDepartment(dept);
        student.setClassEntity(cls);
        student.setEnabled(true);

        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);

        if (!existing.getRegisterNumber().equals(updatedStudent.getRegisterNumber()) &&
                studentRepository.existsByRegisterNumber(updatedStudent.getRegisterNumber())) {
            throw new DuplicateResourceException("Register number already exists.");
        }

        existing.setFullName(updatedStudent.getFullName());
        existing.setRegisterNumber(updatedStudent.getRegisterNumber());
        existing.setPhone(updatedStudent.getPhone());
        existing.setDateOfBirth(updatedStudent.getDateOfBirth());
        existing.setGender(updatedStudent.getGender());
        existing.setAddress(updatedStudent.getAddress());
        existing.setYear(updatedStudent.getYear());
        existing.setSemester(updatedStudent.getSemester());

        if (updatedStudent.getDepartment() != null && updatedStudent.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedStudent.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
            existing.setDepartment(dept);
        }

        if (updatedStudent.getClassEntity() != null && updatedStudent.getClassEntity().getId() != null) {
            ClassEntity cls = classRepository.findById(updatedStudent.getClassEntity().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found."));
            existing.setClassEntity(cls);
        }

        // Update associated user
        if (existing.getUser() != null) {
            User user = existing.getUser();
            user.setFullName(updatedStudent.getFullName());
            userRepository.save(user);
        }

        return studentRepository.save(existing);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        student.setEnabled(false);
        if (student.getUser() != null) {
            student.getUser().setEnabled(false);
            userRepository.save(student.getUser());
        }
        studentRepository.save(student);
    }

    public long countStudents() {
        return studentRepository.countByEnabled(true);
    }

    public long countStudentsByDepartment(Long deptId) {
        return studentRepository.countByDepartmentId(deptId);
    }
}
