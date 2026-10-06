package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_code", nullable = false, unique = true, length = 20)
    private String subjectCode;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer credits = 3;

    @Column(nullable = false)
    private Integer semester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Subject() {}

    public Subject(Long id, String subjectCode, String name, Integer credits, Integer semester, Department department, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.name = name;
        this.credits = credits != null ? credits : 3;
        this.semester = semester;
        this.department = department;
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

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static SubjectBuilder builder() { return new SubjectBuilder(); }

    public static class SubjectBuilder {
        private Long id;
        private String subjectCode;
        private String name;
        private Integer credits = 3;
        private Integer semester;
        private Department department;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public SubjectBuilder id(Long id) { this.id = id; return this; }
        public SubjectBuilder subjectCode(String subjectCode) { this.subjectCode = subjectCode; return this; }
        public SubjectBuilder name(String name) { this.name = name; return this; }
        public SubjectBuilder credits(Integer credits) { this.credits = credits; return this; }
        public SubjectBuilder semester(Integer semester) { this.semester = semester; return this; }
        public SubjectBuilder department(Department department) { this.department = department; return this; }
        public SubjectBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public SubjectBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Subject build() {
            return new Subject(id, subjectCode, name, credits, semester, department, createdAt, updatedAt);
        }
    }
}
