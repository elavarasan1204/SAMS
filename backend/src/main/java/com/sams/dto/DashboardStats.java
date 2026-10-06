package com.sams.dto;

public class DashboardStats {
    private long totalStudents;
    private long totalFaculty;
    private long totalHods;
    private long totalDepartments;
    private long totalSubjects;
    private long totalClasses;

    public DashboardStats() {}

    public DashboardStats(long totalStudents, long totalFaculty, long totalHods, long totalDepartments, long totalSubjects, long totalClasses) {
        this.totalStudents = totalStudents;
        this.totalFaculty = totalFaculty;
        this.totalHods = totalHods;
        this.totalDepartments = totalDepartments;
        this.totalSubjects = totalSubjects;
        this.totalClasses = totalClasses;
    }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; }

    public long getTotalHods() { return totalHods; }
    public void setTotalHods(long totalHods) { this.totalHods = totalHods; }

    public long getTotalDepartments() { return totalDepartments; }
    public void setTotalDepartments(long totalDepartments) { this.totalDepartments = totalDepartments; }

    public long getTotalSubjects() { return totalSubjects; }
    public void setTotalSubjects(long totalSubjects) { this.totalSubjects = totalSubjects; }

    public long getTotalClasses() { return totalClasses; }
    public void setTotalClasses(long totalClasses) { this.totalClasses = totalClasses; }

    public static DashboardStatsBuilder builder() { return new DashboardStatsBuilder(); }

    public static class DashboardStatsBuilder {
        private long totalStudents;
        private long totalFaculty;
        private long totalHods;
        private long totalDepartments;
        private long totalSubjects;
        private long totalClasses;

        public DashboardStatsBuilder totalStudents(long totalStudents) { this.totalStudents = totalStudents; return this; }
        public DashboardStatsBuilder totalFaculty(long totalFaculty) { this.totalFaculty = totalFaculty; return this; }
        public DashboardStatsBuilder totalHods(long totalHods) { this.totalHods = totalHods; return this; }
        public DashboardStatsBuilder totalDepartments(long totalDepartments) { this.totalDepartments = totalDepartments; return this; }
        public DashboardStatsBuilder totalSubjects(long totalSubjects) { this.totalSubjects = totalSubjects; return this; }
        public DashboardStatsBuilder totalClasses(long totalClasses) { this.totalClasses = totalClasses; return this; }

        public DashboardStats build() {
            return new DashboardStats(totalStudents, totalFaculty, totalHods, totalDepartments, totalSubjects, totalClasses);
        }
    }
}
