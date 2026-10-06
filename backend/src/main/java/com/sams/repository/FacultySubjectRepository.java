package com.sams.repository;

import com.sams.entity.FacultySubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FacultySubjectRepository extends JpaRepository<FacultySubject, Long> {
    List<FacultySubject> findByFacultyId(Long facultyId);
    List<FacultySubject> findBySubjectId(Long subjectId);
    List<FacultySubject> findByClassEntityId(Long classId);
    List<FacultySubject> findByFacultyIdAndAcademicYear(Long facultyId, String academicYear);
    boolean existsByFacultyIdAndSubjectIdAndClassEntityIdAndAcademicYear(
            Long facultyId, Long subjectId, Long classId, String academicYear);
}
