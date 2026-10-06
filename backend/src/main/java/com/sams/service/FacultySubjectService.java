package com.sams.service;

import com.sams.entity.*;
import com.sams.exception.*;
import com.sams.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultySubjectService {

    @Autowired
    private FacultySubjectRepository facultySubjectRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRepository classRepository;

    public List<FacultySubject> getAllAssignments() {
        return facultySubjectRepository.findAll();
    }

    public List<FacultySubject> getAssignmentsByFaculty(Long facultyId) {
        return facultySubjectRepository.findByFacultyId(facultyId);
    }

    public List<FacultySubject> getAssignmentsBySubject(Long subjectId) {
        return facultySubjectRepository.findBySubjectId(subjectId);
    }

    public List<FacultySubject> getAssignmentsByClass(Long classId) {
        return facultySubjectRepository.findByClassEntityId(classId);
    }

    public FacultySubject createAssignment(FacultySubject assignment) {
        if (facultySubjectRepository.existsByFacultyIdAndSubjectIdAndClassEntityIdAndAcademicYear(
                assignment.getFaculty().getId(),
                assignment.getSubject().getId(),
                assignment.getClassEntity().getId(),
                assignment.getAcademicYear())) {
            throw new DuplicateResourceException("This assignment already exists.");
        }

        Faculty faculty = facultyRepository.findById(assignment.getFaculty().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty not found."));
        Subject subject = subjectRepository.findById(assignment.getSubject().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found."));
        ClassEntity cls = classRepository.findById(assignment.getClassEntity().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found."));

        assignment.setFaculty(faculty);
        assignment.setSubject(subject);
        assignment.setClassEntity(cls);

        return facultySubjectRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        FacultySubject fs = facultySubjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found."));
        facultySubjectRepository.delete(fs);
    }
}
