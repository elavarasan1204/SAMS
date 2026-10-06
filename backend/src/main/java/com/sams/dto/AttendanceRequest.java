package com.sams.dto;

import java.util.List;
import java.time.LocalDate;

public class AttendanceRequest {
    private Long subjectId;
    private Long classId;
    private LocalDate date;
    private List<AttendanceEntry> attendanceList;

    public AttendanceRequest() {}

    public AttendanceRequest(Long subjectId, Long classId, LocalDate date, List<AttendanceEntry> attendanceList) {
        this.subjectId = subjectId;
        this.classId = classId;
        this.date = date;
        this.attendanceList = attendanceList;
    }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<AttendanceEntry> getAttendanceList() { return attendanceList; }
    public void setAttendanceList(List<AttendanceEntry> attendanceList) { this.attendanceList = attendanceList; }

    public static AttendanceRequestBuilder builder() { return new AttendanceRequestBuilder(); }

    public static class AttendanceRequestBuilder {
        private Long subjectId;
        private Long classId;
        private LocalDate date;
        private List<AttendanceEntry> attendanceList;

        public AttendanceRequestBuilder subjectId(Long subjectId) { this.subjectId = subjectId; return this; }
        public AttendanceRequestBuilder classId(Long classId) { this.classId = classId; return this; }
        public AttendanceRequestBuilder date(LocalDate date) { this.date = date; return this; }
        public AttendanceRequestBuilder attendanceList(List<AttendanceEntry> attendanceList) { this.attendanceList = attendanceList; return this; }

        public AttendanceRequest build() {
            return new AttendanceRequest(subjectId, classId, date, attendanceList);
        }
    }

    public static class AttendanceEntry {
        private Long studentId;
        private String status;

        public AttendanceEntry() {}

        public AttendanceEntry(Long studentId, String status) {
            this.studentId = studentId;
            this.status = status;
        }

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public static AttendanceEntryBuilder builder() { return new AttendanceEntryBuilder(); }

        public static class AttendanceEntryBuilder {
            private Long studentId;
            private String status;

            public AttendanceEntryBuilder studentId(Long studentId) { this.studentId = studentId; return this; }
            public AttendanceEntryBuilder status(String status) { this.status = status; return this; }

            public AttendanceEntry build() {
                return new AttendanceEntry(studentId, status);
            }
        }
    }
}
