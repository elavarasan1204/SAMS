package com.sams.repository;

import com.sams.entity.Hod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface HodRepository extends JpaRepository<Hod, Long> {
    Optional<Hod> findByEmployeeId(String employeeId);
    Optional<Hod> findByUserId(Long userId);
    Optional<Hod> findByDepartmentId(Long departmentId);
    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
    boolean existsByDepartmentId(Long departmentId);
    List<Hod> findByEnabled(Boolean enabled);
}
