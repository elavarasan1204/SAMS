package com.sams.service;

import com.sams.entity.*;
import com.sams.exception.*;
import com.sams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<ClassEntity> getAllClasses() {
        return classRepository.findAll();
    }

    public ClassEntity getClassById(Long id) {
        return classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
    }

    public List<ClassEntity> getClassesByDepartment(Long departmentId) {
        return classRepository.findByDepartmentId(departmentId);
    }

    public ClassEntity createClass(ClassEntity classEntity) {
        if (classRepository.existsByNameAndDepartmentId(classEntity.getName(), classEntity.getDepartment().getId())) {
            throw new DuplicateResourceException("Class '" + classEntity.getName() + "' already exists in this department.");
        }
        Department dept = departmentRepository.findById(classEntity.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
        classEntity.setDepartment(dept);
        return classRepository.save(classEntity);
    }

    public ClassEntity updateClass(Long id, ClassEntity updatedClass) {
        ClassEntity existing = getClassById(id);
        existing.setName(updatedClass.getName());
        existing.setYear(updatedClass.getYear());
        existing.setSection(updatedClass.getSection());
        existing.setSemester(updatedClass.getSemester());
        if (updatedClass.getDepartment() != null && updatedClass.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedClass.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
            existing.setDepartment(dept);
        }
        return classRepository.save(existing);
    }

    public void deleteClass(Long id) {
        ClassEntity classEntity = getClassById(id);
        classRepository.delete(classEntity);
    }

    public long countClasses() {
        return classRepository.count();
    }
}
