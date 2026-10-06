package com.sams.repository;

import com.sams.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByEmployeeId(String employeeId);
    Optional<Faculty> findByUserId(Long userId);
    Optional<Faculty> findByEmail(String email);
    List<Faculty> findByDepartmentId(Long departmentId);
    List<Faculty> findByEnabled(Boolean enabled);
    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
    long countByEnabled(Boolean enabled);
    long countByDepartmentId(Long departmentId);

    @Query("SELECT f FROM Faculty f WHERE " +
           "LOWER(f.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(f.employeeId) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Faculty> searchFaculty(@Param("search") String search);
}
