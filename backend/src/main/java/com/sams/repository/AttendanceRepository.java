package com.sams.repository;

import com.sams.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findBySubjectId(Long subjectId);
    List<Attendance> findByClassEntityId(Long classId);
    List<Attendance> findByFacultyId(Long facultyId);
    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    List<Attendance> findBySubjectIdAndClassEntityIdAndDate(Long subjectId, Long classId, LocalDate date);
    List<Attendance> findByClassEntityIdAndDate(Long classId, LocalDate date);
    List<Attendance> findByFacultyIdAndDate(Long facultyId, LocalDate date);

    Optional<Attendance> findByStudentIdAndSubjectIdAndDate(Long studentId, Long subjectId, LocalDate date);
    boolean existsByStudentIdAndSubjectIdAndDate(Long studentId, Long subjectId, LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.subject.id = :subjectId")
    long countTotalClasses(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.subject.id = :subjectId AND a.status = 'PRESENT'")
    long countPresentClasses(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId")
    long countTotalByStudent(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.id = :studentId AND a.status = 'PRESENT'")
    long countPresentByStudent(@Param("studentId") Long studentId);

    @Query("SELECT DISTINCT a.subject.id FROM Attendance a WHERE a.student.id = :studentId")
    List<Long> findDistinctSubjectIdsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT DISTINCT a.date FROM Attendance a WHERE a.subject.id = :subjectId AND a.classEntity.id = :classId ORDER BY a.date DESC")
    List<LocalDate> findDistinctDatesBySubjectAndClass(@Param("subjectId") Long subjectId, @Param("classId") Long classId);

    @Query("SELECT COUNT(DISTINCT a.date) FROM Attendance a WHERE a.subject.id = :subjectId AND a.classEntity.id = :classId")
    long countDistinctDatesBySubjectAndClass(@Param("subjectId") Long subjectId, @Param("classId") Long classId);

    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate start, LocalDate end);
    List<Attendance> findBySubjectIdAndDateBetween(Long subjectId, LocalDate start, LocalDate end);

    @Query("SELECT a FROM Attendance a WHERE a.classEntity.department.id = :deptId")
    List<Attendance> findByDepartmentId(@Param("deptId") Long deptId);
}
