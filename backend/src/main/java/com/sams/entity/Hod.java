package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hod")
public class Hod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false, unique = true, length = 20)
    private String employeeId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(length = 100)
    private String qualification;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Hod() {}

    public Hod(Long id, String employeeId, String fullName, String email, String phone, String qualification, Department department, User user, Boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
        this.department = department;
        this.user = user;
        this.enabled = enabled != null ? enabled : true;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static HodBuilder builder() { return new HodBuilder(); }

    public static class HodBuilder {
        private Long id;
        private String employeeId;
        private String fullName;
        private String email;
        private String phone;
        private String qualification;
        private Department department;
        private User user;
        private Boolean enabled = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public HodBuilder id(Long id) { this.id = id; return this; }
        public HodBuilder employeeId(String employeeId) { this.employeeId = employeeId; return this; }
        public HodBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public HodBuilder email(String email) { this.email = email; return this; }
        public HodBuilder phone(String phone) { this.phone = phone; return this; }
        public HodBuilder qualification(String qualification) { this.qualification = qualification; return this; }
        public HodBuilder department(Department department) { this.department = department; return this; }
        public HodBuilder user(User user) { this.user = user; return this; }
        public HodBuilder enabled(Boolean enabled) { this.enabled = enabled; return this; }
        public HodBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public HodBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Hod build() {
            return new Hod(id, employeeId, fullName, email, phone, qualification, department, user, enabled, createdAt, updatedAt);
        }
    }
}
