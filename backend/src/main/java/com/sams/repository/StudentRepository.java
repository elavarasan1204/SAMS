package com.sams.repository;

import com.sams.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRegisterNumber(String registerNumber);
    Optional<Student> findByUserId(Long userId);
    Optional<Student> findByEmail(String email);
    List<Student> findByDepartmentId(Long departmentId);
    List<Student> findByClassEntityId(Long classId);
    List<Student> findByDepartmentIdAndClassEntityId(Long departmentId, Long classId);
    List<Student> findByEnabled(Boolean enabled);
    boolean existsByRegisterNumber(String registerNumber);
    boolean existsByEmail(String email);
    long countByEnabled(Boolean enabled);
    long countByDepartmentId(Long departmentId);

    @Query("SELECT s FROM Student s WHERE " +
           "(LOWER(s.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.registerNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Student> searchStudents(@Param("search") String search);

    @Query("SELECT s FROM Student s WHERE s.department.id = :deptId AND " +
           "(LOWER(s.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.registerNumber) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Student> searchStudentsByDepartment(@Param("deptId") Long deptId, @Param("search") String search);
}
