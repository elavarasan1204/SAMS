package com.sams.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "subject_id", "date"}))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity classEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "marked_at", updatable = false)
    private LocalDateTime markedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Attendance() {}

    public Attendance(Long id, Student student, Subject subject, ClassEntity classEntity, Faculty faculty, LocalDate date, Status status, LocalDateTime markedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.classEntity = classEntity;
        this.faculty = faculty;
        this.date = date;
        this.status = status;
        this.markedAt = markedAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        markedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Status {
        PRESENT, ABSENT
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public ClassEntity getClassEntity() { return classEntity; }
    public void setClassEntity(ClassEntity classEntity) { this.classEntity = classEntity; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getMarkedAt() { return markedAt; }
    public void setMarkedAt(LocalDateTime markedAt) { this.markedAt = markedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static AttendanceBuilder builder() { return new AttendanceBuilder(); }

    public static class AttendanceBuilder {
        private Long id;
        private Student student;
        private Subject subject;
        private ClassEntity classEntity;
        private Faculty faculty;
        private LocalDate date;
        private Status status;
        private LocalDateTime markedAt;
        private LocalDateTime updatedAt;

        public AttendanceBuilder id(Long id) { this.id = id; return this; }
        public AttendanceBuilder student(Student student) { this.student = student; return this; }
        public AttendanceBuilder subject(Subject subject) { this.subject = subject; return this; }
        public AttendanceBuilder classEntity(ClassEntity classEntity) { this.classEntity = classEntity; return this; }
        public AttendanceBuilder faculty(Faculty faculty) { this.faculty = faculty; return this; }
        public AttendanceBuilder date(LocalDate date) { this.date = date; return this; }
        public AttendanceBuilder status(Status status) { this.status = status; return this; }
        public AttendanceBuilder markedAt(LocalDateTime markedAt) { this.markedAt = markedAt; return this; }
        public AttendanceBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Attendance build() {
            return new Attendance(id, student, subject, classEntity, faculty, date, status, markedAt, updatedAt);
        }
    }
}
