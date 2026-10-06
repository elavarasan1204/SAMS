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
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with id: " + id));
    }

    public Faculty getFacultyByUserId(Long userId) {
        return facultyRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found for user id: " + userId));
    }

    public List<Faculty> getFacultyByDepartment(Long departmentId) {
        return facultyRepository.findByDepartmentId(departmentId);
    }

    public List<Faculty> searchFaculty(String search) {
        return facultyRepository.searchFaculty(search);
    }

    @Transactional
    public Faculty createFaculty(Faculty faculty, String password) {
        if (facultyRepository.existsByEmployeeId(faculty.getEmployeeId())) {
            throw new DuplicateResourceException("Faculty with employee ID " + faculty.getEmployeeId() + " already exists.");
        }
        if (facultyRepository.existsByEmail(faculty.getEmail())) {
            throw new DuplicateResourceException("Faculty with email " + faculty.getEmail() + " already exists.");
        }

        Department dept = departmentRepository.findById(faculty.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));

        String username = faculty.getEmployeeId().toLowerCase();
        if (userRepository.existsByUsername(username)) {
            username = faculty.getEmail().split("@")[0];
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(faculty.getEmail())
                .fullName(faculty.getFullName())
                .role(User.Role.FACULTY)
                .enabled(true)
                .build();
        user = userRepository.save(user);

        faculty.setUser(user);
        faculty.setDepartment(dept);
        faculty.setEnabled(true);

        return facultyRepository.save(faculty);
    }

    @Transactional
    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        Faculty existing = getFacultyById(id);

        if (!existing.getEmployeeId().equals(updatedFaculty.getEmployeeId()) &&
                facultyRepository.existsByEmployeeId(updatedFaculty.getEmployeeId())) {
            throw new DuplicateResourceException("Employee ID already exists.");
        }

        existing.setFullName(updatedFaculty.getFullName());
        existing.setEmployeeId(updatedFaculty.getEmployeeId());
        existing.setPhone(updatedFaculty.getPhone());
        existing.setQualification(updatedFaculty.getQualification());
        existing.setDesignation(updatedFaculty.getDesignation());

        if (updatedFaculty.getDepartment() != null && updatedFaculty.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedFaculty.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
            existing.setDepartment(dept);
        }

        if (existing.getUser() != null) {
            User user = existing.getUser();
            user.setFullName(updatedFaculty.getFullName());
            userRepository.save(user);
        }

        return facultyRepository.save(existing);
    }

    @Transactional
    public void deleteFaculty(Long id) {
        Faculty faculty = getFacultyById(id);
        faculty.setEnabled(false);
        if (faculty.getUser() != null) {
            faculty.getUser().setEnabled(false);
            userRepository.save(faculty.getUser());
        }
        facultyRepository.save(faculty);
    }

    public long countFaculty() {
        return facultyRepository.countByEnabled(true);
    }

    public long countFacultyByDepartment(Long deptId) {
        return facultyRepository.countByDepartmentId(deptId);
    }
}
