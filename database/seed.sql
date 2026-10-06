-- ============================================
-- Student Attendance Management System (SAMS)
-- Seed Data
-- ============================================

USE student_attendance_db;

-- ============================================
-- BCrypt hashed passwords
-- Admin@123  -> $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- Hod@123    -> $2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQkLgcI/G7TYfKshBEnDrCzLGTJ2H6
-- Faculty@123-> $2a$10$Wss2QCDzLOEzGkiB8VKhAeXMdHxKHMVvSxSbl5UXzmEliU6.xb3Oe
-- Student@123-> $2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK
-- ============================================

-- Users (passwords are BCrypt hashed)
INSERT INTO users (username, password, email, full_name, role, enabled) VALUES
('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@sams.edu', 'System Administrator', 'ADMIN', true),
('hod', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQkLgcI/G7TYfKshBEnDrCzLGTJ2H6', 'hod.cse@sams.edu', 'Dr. Rajesh Kumar', 'HOD', true),
('faculty', '$2a$10$Wss2QCDzLOEzGkiB8VKhAeXMdHxKHMVvSxSbl5UXzmEliU6.xb3Oe', 'faculty1@sams.edu', 'Prof. Anitha Sharma', 'FACULTY', true),
('faculty2', '$2a$10$Wss2QCDzLOEzGkiB8VKhAeXMdHxKHMVvSxSbl5UXzmEliU6.xb3Oe', 'faculty2@sams.edu', 'Prof. Suresh Babu', 'FACULTY', true),
('faculty3', '$2a$10$Wss2QCDzLOEzGkiB8VKhAeXMdHxKHMVvSxSbl5UXzmEliU6.xb3Oe', 'faculty3@sams.edu', 'Prof. Meena Devi', 'FACULTY', true),
('student', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student1@sams.edu', 'Arun Kumar', 'STUDENT', true),
('student2', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student2@sams.edu', 'Priya Lakshmi', 'STUDENT', true),
('student3', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student3@sams.edu', 'Karthik Rajan', 'STUDENT', true),
('student4', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student4@sams.edu', 'Divya Sundaram', 'STUDENT', true),
('student5', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student5@sams.edu', 'Venkatesh Iyer', 'STUDENT', true),
('student6', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student6@sams.edu', 'Sneha Reddy', 'STUDENT', true),
('student7', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student7@sams.edu', 'Manoj Prasad', 'STUDENT', true),
('student8', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student8@sams.edu', 'Lakshmi Narayanan', 'STUDENT', true),
('student9', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student9@sams.edu', 'Ramya Krishnan', 'STUDENT', true),
('student10', '$2a$10$mB7UIHBFL6/giCOnkz0Wbemm0SwDNA0OQ4Xyc/RnU5dbFWn8KPVHK', 'student10@sams.edu', 'Ganesh Moorthy', 'STUDENT', true);

-- Departments
INSERT INTO departments (name, code, description) VALUES
('Computer Science and Engineering', 'CSE', 'Department of Computer Science and Engineering'),
('Electronics and Communication Engineering', 'ECE', 'Department of Electronics and Communication Engineering'),
('Mechanical Engineering', 'MECH', 'Department of Mechanical Engineering');

-- Classes
INSERT INTO classes (name, year, section, semester, department_id) VALUES
('CSE III A', 3, 'A', 5, 1),
('CSE III B', 3, 'B', 5, 1),
('CSE II A', 2, 'A', 3, 1),
('ECE III A', 3, 'A', 5, 2);

-- HOD
INSERT INTO hod (employee_id, full_name, email, phone, qualification, department_id, user_id, enabled) VALUES
('HOD001', 'Dr. Rajesh Kumar', 'hod.cse@sams.edu', '9876543210', 'Ph.D. in Computer Science', 1, 2, true);

-- Faculty
INSERT INTO faculty (employee_id, full_name, email, phone, qualification, designation, department_id, user_id, enabled) VALUES
('FAC001', 'Prof. Anitha Sharma', 'faculty1@sams.edu', '9876543211', 'M.Tech in Software Engineering', 'Assistant Professor', 1, 3, true),
('FAC002', 'Prof. Suresh Babu', 'faculty2@sams.edu', '9876543212', 'M.Tech in DBMS', 'Associate Professor', 1, 4, true),
('FAC003', 'Prof. Meena Devi', 'faculty3@sams.edu', '9876543213', 'M.Tech in OS', 'Assistant Professor', 1, 5, true);

-- Students
INSERT INTO students (register_number, full_name, email, phone, date_of_birth, gender, department_id, class_id, year, semester, user_id, enabled) VALUES
('2024CSE001', 'Arun Kumar', 'student1@sams.edu', '9876500001', '2004-05-15', 'MALE', 1, 1, 3, 5, 6, true),
('2024CSE002', 'Priya Lakshmi', 'student2@sams.edu', '9876500002', '2004-03-22', 'FEMALE', 1, 1, 3, 5, 7, true),
('2024CSE003', 'Karthik Rajan', 'student3@sams.edu', '9876500003', '2004-08-10', 'MALE', 1, 1, 3, 5, 8, true),
('2024CSE004', 'Divya Sundaram', 'student4@sams.edu', '9876500004', '2004-01-28', 'FEMALE', 1, 1, 3, 5, 9, true),
('2024CSE005', 'Venkatesh Iyer', 'student5@sams.edu', '9876500005', '2004-11-03', 'MALE', 1, 1, 3, 5, 10, true),
('2024CSE006', 'Sneha Reddy', 'student6@sams.edu', '9876500006', '2004-07-19', 'FEMALE', 1, 2, 3, 5, 11, true),
('2024CSE007', 'Manoj Prasad', 'student7@sams.edu', '9876500007', '2004-09-25', 'MALE', 1, 2, 3, 5, 12, true),
('2024CSE008', 'Lakshmi Narayanan', 'student8@sams.edu', '9876500008', '2004-02-14', 'FEMALE', 1, 2, 3, 5, 13, true),
('2024CSE009', 'Ramya Krishnan', 'student9@sams.edu', '9876500009', '2004-06-30', 'FEMALE', 1, 2, 3, 5, 14, true),
('2024CSE010', 'Ganesh Moorthy', 'student10@sams.edu', '9876500010', '2004-04-08', 'MALE', 1, 2, 3, 5, 15, true);

-- Subjects
INSERT INTO subjects (subject_code, name, credits, semester, department_id) VALUES
('CS301', 'Software Engineering', 4, 5, 1),
('CS302', 'Database Management Systems', 4, 5, 1),
('CS303', 'Operating Systems', 3, 5, 1),
('CS304', 'Web Technology', 3, 5, 1),
('CS305', 'Design and Analysis of Algorithms', 4, 5, 1);

-- Faculty-Subject Assignments
INSERT INTO faculty_subjects (faculty_id, subject_id, class_id, academic_year) VALUES
(1, 1, 1, '2024-2025'),  -- Anitha -> Software Engineering -> CSE III A
(1, 1, 2, '2024-2025'),  -- Anitha -> Software Engineering -> CSE III B
(2, 2, 1, '2024-2025'),  -- Suresh -> DBMS -> CSE III A
(2, 2, 2, '2024-2025'),  -- Suresh -> DBMS -> CSE III B
(3, 3, 1, '2024-2025'),  -- Meena -> OS -> CSE III A
(3, 3, 2, '2024-2025'),  -- Meena -> OS -> CSE III B
(1, 4, 1, '2024-2025'),  -- Anitha -> Web Tech -> CSE III A
(2, 5, 1, '2024-2025');  -- Suresh -> DAA -> CSE III A

-- ============================================
-- Attendance Records (Realistic Demo Data)
-- Sept 2024 - generating attendance for CS301 Software Engineering
-- ============================================

-- CS301 - Software Engineering - CSE III A (Faculty: Anitha)
-- Date: 2024-09-02
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-02', 'PRESENT'),
(2, 1, 1, 1, '2024-09-02', 'PRESENT'),
(3, 1, 1, 1, '2024-09-02', 'ABSENT'),
(4, 1, 1, 1, '2024-09-02', 'PRESENT'),
(5, 1, 1, 1, '2024-09-02', 'PRESENT');

-- Date: 2024-09-03
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-03', 'PRESENT'),
(2, 1, 1, 1, '2024-09-03', 'ABSENT'),
(3, 1, 1, 1, '2024-09-03', 'PRESENT'),
(4, 1, 1, 1, '2024-09-03', 'PRESENT'),
(5, 1, 1, 1, '2024-09-03', 'ABSENT');

-- Date: 2024-09-04
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-04', 'PRESENT'),
(2, 1, 1, 1, '2024-09-04', 'PRESENT'),
(3, 1, 1, 1, '2024-09-04', 'PRESENT'),
(4, 1, 1, 1, '2024-09-04', 'ABSENT'),
(5, 1, 1, 1, '2024-09-04', 'PRESENT');

-- Date: 2024-09-05
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-05', 'PRESENT'),
(2, 1, 1, 1, '2024-09-05', 'PRESENT'),
(3, 1, 1, 1, '2024-09-05', 'ABSENT'),
(4, 1, 1, 1, '2024-09-05', 'PRESENT'),
(5, 1, 1, 1, '2024-09-05', 'PRESENT');

-- Date: 2024-09-06
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-06', 'ABSENT'),
(2, 1, 1, 1, '2024-09-06', 'PRESENT'),
(3, 1, 1, 1, '2024-09-06', 'PRESENT'),
(4, 1, 1, 1, '2024-09-06', 'PRESENT'),
(5, 1, 1, 1, '2024-09-06', 'PRESENT');

-- Date: 2024-09-09
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-09', 'PRESENT'),
(2, 1, 1, 1, '2024-09-09', 'PRESENT'),
(3, 1, 1, 1, '2024-09-09', 'ABSENT'),
(4, 1, 1, 1, '2024-09-09', 'PRESENT'),
(5, 1, 1, 1, '2024-09-09', 'PRESENT');

-- Date: 2024-09-10
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-10', 'PRESENT'),
(2, 1, 1, 1, '2024-09-10', 'PRESENT'),
(3, 1, 1, 1, '2024-09-10', 'PRESENT'),
(4, 1, 1, 1, '2024-09-10', 'PRESENT'),
(5, 1, 1, 1, '2024-09-10', 'ABSENT');

-- Date: 2024-09-11
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-11', 'PRESENT'),
(2, 1, 1, 1, '2024-09-11', 'ABSENT'),
(3, 1, 1, 1, '2024-09-11', 'ABSENT'),
(4, 1, 1, 1, '2024-09-11', 'PRESENT'),
(5, 1, 1, 1, '2024-09-11', 'PRESENT');

-- Date: 2024-09-12
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-12', 'PRESENT'),
(2, 1, 1, 1, '2024-09-12', 'PRESENT'),
(3, 1, 1, 1, '2024-09-12', 'PRESENT'),
(4, 1, 1, 1, '2024-09-12', 'PRESENT'),
(5, 1, 1, 1, '2024-09-12', 'PRESENT');

-- Date: 2024-09-13
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 1, 1, 1, '2024-09-13', 'PRESENT'),
(2, 1, 1, 1, '2024-09-13', 'PRESENT'),
(3, 1, 1, 1, '2024-09-13', 'ABSENT'),
(4, 1, 1, 1, '2024-09-13', 'PRESENT'),
(5, 1, 1, 1, '2024-09-13', 'PRESENT');

-- CS302 - DBMS - CSE III A (Faculty: Suresh)
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 2, 1, 2, '2024-09-02', 'PRESENT'),
(2, 2, 1, 2, '2024-09-02', 'PRESENT'),
(3, 2, 1, 2, '2024-09-02', 'PRESENT'),
(4, 2, 1, 2, '2024-09-02', 'PRESENT'),
(5, 2, 1, 2, '2024-09-02', 'ABSENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 2, 1, 2, '2024-09-04', 'PRESENT'),
(2, 2, 1, 2, '2024-09-04', 'ABSENT'),
(3, 2, 1, 2, '2024-09-04', 'PRESENT'),
(4, 2, 1, 2, '2024-09-04', 'PRESENT'),
(5, 2, 1, 2, '2024-09-04', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 2, 1, 2, '2024-09-06', 'PRESENT'),
(2, 2, 1, 2, '2024-09-06', 'PRESENT'),
(3, 2, 1, 2, '2024-09-06', 'ABSENT'),
(4, 2, 1, 2, '2024-09-06', 'ABSENT'),
(5, 2, 1, 2, '2024-09-06', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 2, 1, 2, '2024-09-09', 'PRESENT'),
(2, 2, 1, 2, '2024-09-09', 'PRESENT'),
(3, 2, 1, 2, '2024-09-09', 'PRESENT'),
(4, 2, 1, 2, '2024-09-09', 'PRESENT'),
(5, 2, 1, 2, '2024-09-09', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 2, 1, 2, '2024-09-11', 'ABSENT'),
(2, 2, 1, 2, '2024-09-11', 'PRESENT'),
(3, 2, 1, 2, '2024-09-11', 'PRESENT'),
(4, 2, 1, 2, '2024-09-11', 'PRESENT'),
(5, 2, 1, 2, '2024-09-11', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 2, 1, 2, '2024-09-13', 'PRESENT'),
(2, 2, 1, 2, '2024-09-13', 'PRESENT'),
(3, 2, 1, 2, '2024-09-13', 'ABSENT'),
(4, 2, 1, 2, '2024-09-13', 'PRESENT'),
(5, 2, 1, 2, '2024-09-13', 'PRESENT');

-- CS303 - OS - CSE III A (Faculty: Meena)
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 3, 1, 3, '2024-09-03', 'PRESENT'),
(2, 3, 1, 3, '2024-09-03', 'PRESENT'),
(3, 3, 1, 3, '2024-09-03', 'PRESENT'),
(4, 3, 1, 3, '2024-09-03', 'PRESENT'),
(5, 3, 1, 3, '2024-09-03', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 3, 1, 3, '2024-09-05', 'PRESENT'),
(2, 3, 1, 3, '2024-09-05', 'ABSENT'),
(3, 3, 1, 3, '2024-09-05', 'ABSENT'),
(4, 3, 1, 3, '2024-09-05', 'PRESENT'),
(5, 3, 1, 3, '2024-09-05', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 3, 1, 3, '2024-09-10', 'PRESENT'),
(2, 3, 1, 3, '2024-09-10', 'PRESENT'),
(3, 3, 1, 3, '2024-09-10', 'PRESENT'),
(4, 3, 1, 3, '2024-09-10', 'ABSENT'),
(5, 3, 1, 3, '2024-09-10', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(1, 3, 1, 3, '2024-09-12', 'ABSENT'),
(2, 3, 1, 3, '2024-09-12', 'PRESENT'),
(3, 3, 1, 3, '2024-09-12', 'PRESENT'),
(4, 3, 1, 3, '2024-09-12', 'PRESENT'),
(5, 3, 1, 3, '2024-09-12', 'ABSENT');

-- Attendance for CSE III B students
INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(6, 1, 2, 1, '2024-09-02', 'PRESENT'),
(7, 1, 2, 1, '2024-09-02', 'ABSENT'),
(8, 1, 2, 1, '2024-09-02', 'PRESENT'),
(9, 1, 2, 1, '2024-09-02', 'PRESENT'),
(10, 1, 2, 1, '2024-09-02', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(6, 1, 2, 1, '2024-09-04', 'PRESENT'),
(7, 1, 2, 1, '2024-09-04', 'PRESENT'),
(8, 1, 2, 1, '2024-09-04', 'ABSENT'),
(9, 1, 2, 1, '2024-09-04', 'ABSENT'),
(10, 1, 2, 1, '2024-09-04', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(6, 1, 2, 1, '2024-09-06', 'PRESENT'),
(7, 1, 2, 1, '2024-09-06', 'PRESENT'),
(8, 1, 2, 1, '2024-09-06', 'PRESENT'),
(9, 1, 2, 1, '2024-09-06', 'PRESENT'),
(10, 1, 2, 1, '2024-09-06', 'ABSENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(6, 2, 2, 2, '2024-09-03', 'PRESENT'),
(7, 2, 2, 2, '2024-09-03', 'PRESENT'),
(8, 2, 2, 2, '2024-09-03', 'PRESENT'),
(9, 2, 2, 2, '2024-09-03', 'ABSENT'),
(10, 2, 2, 2, '2024-09-03', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(6, 2, 2, 2, '2024-09-05', 'ABSENT'),
(7, 2, 2, 2, '2024-09-05', 'PRESENT'),
(8, 2, 2, 2, '2024-09-05', 'PRESENT'),
(9, 2, 2, 2, '2024-09-05', 'PRESENT'),
(10, 2, 2, 2, '2024-09-05', 'PRESENT');

INSERT INTO attendance (student_id, subject_id, class_id, faculty_id, date, status) VALUES
(6, 3, 2, 3, '2024-09-04', 'PRESENT'),
(7, 3, 2, 3, '2024-09-04', 'PRESENT'),
(8, 3, 2, 3, '2024-09-04', 'ABSENT'),
(9, 3, 2, 3, '2024-09-04', 'PRESENT'),
(10, 3, 2, 3, '2024-09-04', 'PRESENT');
