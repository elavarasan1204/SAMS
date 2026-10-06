package com.sams.dto;

public class AttendanceReportDTO {
    private Long studentId;
    private String studentName;
    private String registerNumber;
    private String className;
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    private long totalClasses;
    private long presentClasses;
    private long absentClasses;
    private double percentage;
    private String status; // GOOD, WARNING, CRITICAL

    public AttendanceReportDTO() {}

    public AttendanceReportDTO(Long studentId, String studentName, String registerNumber, String className, Long subjectId, String subjectName, String subjectCode, long totalClasses, long presentClasses, long absentClasses, double percentage, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.registerNumber = registerNumber;
        this.className = className;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.totalClasses = totalClasses;
        this.presentClasses = presentClasses;
        this.absentClasses = absentClasses;
        this.percentage = percentage;
        this.status = status;
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRegisterNumber() { return registerNumber; }
    public void setRegisterNumber(String registerNumber) { this.registerNumber = registerNumber; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public long getTotalClasses() { return totalClasses; }
    public void setTotalClasses(long totalClasses) { this.totalClasses = totalClasses; }

    public long getPresentClasses() { return presentClasses; }
    public void setPresentClasses(long presentClasses) { this.presentClasses = presentClasses; }

    public long getAbsentClasses() { return absentClasses; }
    public void setAbsentClasses(long absentClasses) { this.absentClasses = absentClasses; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static AttendanceReportDTOBuilder builder() { return new AttendanceReportDTOBuilder(); }

    public static class AttendanceReportDTOBuilder {
        private Long studentId;
        private String studentName;
        private String registerNumber;
        private String className;
        private Long subjectId;
        private String subjectName;
        private String subjectCode;
        private long totalClasses;
        private long presentClasses;
        private long absentClasses;
        private double percentage;
        private String status;

        public AttendanceReportDTOBuilder studentId(Long studentId) { this.studentId = studentId; return this; }
        public AttendanceReportDTOBuilder studentName(String studentName) { this.studentName = studentName; return this; }
        public AttendanceReportDTOBuilder registerNumber(String registerNumber) { this.registerNumber = registerNumber; return this; }
        public AttendanceReportDTOBuilder className(String className) { this.className = className; return this; }
        public AttendanceReportDTOBuilder subjectId(Long subjectId) { this.subjectId = subjectId; return this; }
        public AttendanceReportDTOBuilder subjectName(String subjectName) { this.subjectName = subjectName; return this; }
        public AttendanceReportDTOBuilder subjectCode(String subjectCode) { this.subjectCode = subjectCode; return this; }
        public AttendanceReportDTOBuilder totalClasses(long totalClasses) { this.totalClasses = totalClasses; return this; }
        public AttendanceReportDTOBuilder presentClasses(long presentClasses) { this.presentClasses = presentClasses; return this; }
        public AttendanceReportDTOBuilder absentClasses(long absentClasses) { this.absentClasses = absentClasses; return this; }
        public AttendanceReportDTOBuilder percentage(double percentage) { this.percentage = percentage; return this; }
        public AttendanceReportDTOBuilder status(String status) { this.status = status; return this; }

        public AttendanceReportDTO build() {
            return new AttendanceReportDTO(studentId, studentName, registerNumber, className, subjectId, subjectName, subjectCode, totalClasses, presentClasses, absentClasses, percentage, status);
        }
    }
}
