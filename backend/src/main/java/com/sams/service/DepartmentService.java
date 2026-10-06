package com.sams.service;

import com.sams.entity.Department;
import com.sams.exception.*;
import com.sams.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new DuplicateResourceException("Department with name '" + department.getName() + "' already exists.");
        }
        if (departmentRepository.existsByCode(department.getCode())) {
            throw new DuplicateResourceException("Department with code '" + department.getCode() + "' already exists.");
        }
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updatedDept) {
        Department existing = getDepartmentById(id);
        existing.setName(updatedDept.getName());
        existing.setCode(updatedDept.getCode());
        existing.setDescription(updatedDept.getDescription());
        return departmentRepository.save(existing);
    }

    public void deleteDepartment(Long id) {
        Department dept = getDepartmentById(id);
        departmentRepository.delete(dept);
    }

    public long countDepartments() {
        return departmentRepository.count();
    }
}
