package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "faculty_subjects",
       uniqueConstraints = @UniqueConstraint(columnNames = {"faculty_id", "subject_id", "class_id", "academic_year"}))
public class FacultySubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @Column(name = "academic_year", nullable = false, length = 20)
    private String academicYear;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public FacultySubject() {}

    public FacultySubject(Long id, Faculty faculty, Subject subject, ClassEntity classEntity, String academicYear, LocalDateTime createdAt) {
        this.id = id;
        this.faculty = faculty;
        this.subject = subject;
        this.classEntity = classEntity;
        this.academicYear = academicYear;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public ClassEntity getClassEntity() { return classEntity; }
    public void setClassEntity(ClassEntity classEntity) { this.classEntity = classEntity; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static FacultySubjectBuilder builder() { return new FacultySubjectBuilder(); }

    public static class FacultySubjectBuilder {
        private Long id;
        private Faculty faculty;
        private Subject subject;
        private ClassEntity classEntity;
        private String academicYear;
        private LocalDateTime createdAt;

        public FacultySubjectBuilder id(Long id) { this.id = id; return this; }
        public FacultySubjectBuilder faculty(Faculty faculty) { this.faculty = faculty; return this; }
        public FacultySubjectBuilder subject(Subject subject) { this.subject = subject; return this; }
        public FacultySubjectBuilder classEntity(ClassEntity classEntity) { this.classEntity = classEntity; return this; }
        public FacultySubjectBuilder academicYear(String academicYear) { this.academicYear = academicYear; return this; }
        public FacultySubjectBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public FacultySubject build() {
            return new FacultySubject(id, faculty, subject, classEntity, academicYear, createdAt);
        }
    }
}
