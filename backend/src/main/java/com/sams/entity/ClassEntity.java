package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "classes")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false, length = 10)
    private String section;

    @Column(nullable = false)
    private Integer semester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ClassEntity() {}

    public ClassEntity(Long id, String name, Integer year, String section, Integer semester, Department department, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.section = section;
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public Integer getSemester() { return semester; }
    public void setSemester(Integer semester) { this.semester = semester; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static ClassEntityBuilder builder() { return new ClassEntityBuilder(); }

    public static class ClassEntityBuilder {
        private Long id;
        private String name;
        private Integer year;
        private String section;
        private Integer semester;
        private Department department;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ClassEntityBuilder id(Long id) { this.id = id; return this; }
        public ClassEntityBuilder name(String name) { this.name = name; return this; }
        public ClassEntityBuilder year(Integer year) { this.year = year; return this; }
        public ClassEntityBuilder section(String section) { this.section = section; return this; }
        public ClassEntityBuilder semester(Integer semester) { this.semester = semester; return this; }
        public ClassEntityBuilder department(Department department) { this.department = department; return this; }
        public ClassEntityBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ClassEntityBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ClassEntity build() {
            return new ClassEntity(id, name, year, section, semester, department, createdAt, updatedAt);
        }
    }
}
