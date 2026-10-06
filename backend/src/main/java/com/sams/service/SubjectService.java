package com.sams.service;

import com.sams.entity.*;
import com.sams.exception.*;
import com.sams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
    }

    public List<Subject> getSubjectsByDepartment(Long departmentId) {
        return subjectRepository.findByDepartmentId(departmentId);
    }

    public Subject createSubject(Subject subject) {
        if (subjectRepository.existsBySubjectCode(subject.getSubjectCode())) {
            throw new DuplicateResourceException("Subject with code '" + subject.getSubjectCode() + "' already exists.");
        }
        Department dept = departmentRepository.findById(subject.getDepartment().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
        subject.setDepartment(dept);
        return subjectRepository.save(subject);
    }

    public Subject updateSubject(Long id, Subject updatedSubject) {
        Subject existing = getSubjectById(id);
        existing.setSubjectCode(updatedSubject.getSubjectCode());
        existing.setName(updatedSubject.getName());
        existing.setCredits(updatedSubject.getCredits());
        existing.setSemester(updatedSubject.getSemester());
        if (updatedSubject.getDepartment() != null && updatedSubject.getDepartment().getId() != null) {
            Department dept = departmentRepository.findById(updatedSubject.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found."));
            existing.setDepartment(dept);
        }
        return subjectRepository.save(existing);
    }

    public void deleteSubject(Long id) {
        Subject subject = getSubjectById(id);
        subjectRepository.delete(subject);
    }

    public long countSubjects() {
        return subjectRepository.count();
    }
}
