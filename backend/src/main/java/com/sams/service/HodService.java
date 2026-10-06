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
public class HodService {

    @Autowired
    private HodRepository hodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Hod> getAllHods() {
        return hodRepository.findAll();
    }

    public Hod getHodById(Long id) {
        return hodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HOD not found with id: " + id));
    }

    public Hod getHodByUserId(Long userId) {
        return hodRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("HOD not found for user id: " + userId));
    }

    public Hod getHodByDepartment(Long deptId) {
        return hodRepository.findByDepartmentId(deptId)
                .orElseThrow(() -> new ResourceNotFoundException("HOD not found for department: " + deptId));
    }

    @Transactional
    public Hod createHod(Hod hod, String password) {
        if (hodRepository.existsByEmployeeId(hod.getEmployeeId())) {
            throw new DuplicateResourceException("HOD with employee ID " + hod.getEmployeeId() + " already exists.");
        }
        if (hodRepository.existsByEmail(hod.getEmail())) {
            throw new DuplicateResourceException("HOD with email " + hod.getEmail() + " already exists.");
        }
        if (hodRepository.existsByDepartmentId(hod.getDepartment().getId())) {
            throw new DuplicateResourceException("An HOD is already assigned to this department.");
        }

        Department dept = departmentRepository.findById(hod.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));

        String username = hod.getEmployeeId().toLowerCase();
        if (userRepository.existsByUsername(username)) {
            username = hod.getEmail().split("@")[0];
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(hod.getEmail())
                .fullName(hod.getFullName())
                .role(User.Role.HOD)
                .enabled(true)
                .build();
        user = userRepository.save(user);

        hod.setUser(user);
        hod.setDepartment(dept);
        hod.setEnabled(true);

        return hodRepository.save(hod);
    }

    @Transactional
    public Hod updateHod(Long id, Hod updatedHod) {
        Hod existing = getHodById(id);

        existing.setFullName(updatedHod.getFullName());
        existing.setEmployeeId(updatedHod.getEmployeeId());
        existing.setPhone(updatedHod.getPhone());
        existing.setQualification(updatedHod.getQualification());

        if (updatedHod.getDepartment() != null && updatedHod.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedHod.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
            existing.setDepartment(dept);
        }

        if (existing.getUser() != null) {
            User user = existing.getUser();
            user.setFullName(updatedHod.getFullName());
            userRepository.save(user);
        }

        return hodRepository.save(existing);
    }

    @Transactional
    public void deleteHod(Long id) {
        Hod hod = getHodById(id);
        hod.setEnabled(false);
        if (hod.getUser() != null) {
            hod.getUser().setEnabled(false);
            userRepository.save(hod.getUser());
        }
        hodRepository.save(hod);
    }

    public long countHods() {
        return hodRepository.findByEnabled(true).size();
    }
}
