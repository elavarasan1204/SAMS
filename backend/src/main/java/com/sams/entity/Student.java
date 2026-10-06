package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "register_number", nullable = false, unique = true, length = 20)
    private String registerNumber;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 15)
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer semester;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Student() {}

    public Student(Long id, String registerNumber, String fullName, String email, String phone, LocalDate dateOfBirth, Gender gender, String address, Department department, ClassEntity classEntity, Integer year, Integer semester, User user, Boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.registerNumber = registerNumber;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.department = department;
        this.classEntity = classEntity;
        this.year = year;
        this.semester = semester;
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

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRegisterNumber() { return registerNumber; }
    public void setRegisterNumber(String registerNumber) { this.registerNumber = registerNumber; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public ClassEntity getClassEntity() { return classEntity; }
    public void setClassEntity(ClassEntity classEntity) { this.classEntity = classEntity; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static StudentBuilder builder() { return new StudentBuilder(); }

    public static class StudentBuilder {
        private Long id;
        private String registerNumber;
        private String fullName;
        private String email;
        private String phone;
        private LocalDate dateOfBirth;
        private Gender gender;
        private String address;
        private Department department;
        private ClassEntity classEntity;
        private Integer year;
        private Integer semester;
        private User user;
        private Boolean enabled = true;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public StudentBuilder id(Long id) { this.id = id; return this; }
        public StudentBuilder registerNumber(String registerNumber) { this.registerNumber = registerNumber; return this; }
        public StudentBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public StudentBuilder email(String email) { this.email = email; return this; }
        public StudentBuilder phone(String phone) { this.phone = phone; return this; }
        public StudentBuilder dateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; return this; }
        public StudentBuilder gender(Gender gender) { this.gender = gender; return this; }
        public StudentBuilder address(String address) { this.address = address; return this; }
        public StudentBuilder department(Department department) { this.department = department; return this; }
        public StudentBuilder classEntity(ClassEntity classEntity) { this.classEntity = classEntity; return this; }
        public StudentBuilder year(Integer year) { this.year = year; return this; }
        public StudentBuilder semester(Integer semester) { this.semester = semester; return this; }
        public StudentBuilder user(User user) { this.user = user; return this; }
        public StudentBuilder enabled(Boolean enabled) { this.enabled = enabled; return this; }
        public StudentBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public StudentBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Student build() {
            return new Student(id, registerNumber, fullName, email, phone, dateOfBirth, gender, address, department, classEntity, year, semester, user, enabled, createdAt, updatedAt);
        }
    }
}
