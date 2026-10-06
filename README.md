# Student Attendance Management System (SAMS)

A complete full-stack web application for managing student attendance in educational institutions. Built with React.js, Spring Boot, and MySQL.

## Features

### Multi-Role Access
- **Admin**: Full system management (students, faculty, HODs, departments, subjects, classes, assignments, reports)
- **HOD**: Department-specific management and low attendance monitoring
- **Faculty**: Attendance marking for assigned subjects/classes, attendance history
- **Student**: View attendance, profile, subject-wise reports with status indicators

### Core Features
- JWT-based authentication with BCrypt password hashing
- Role-based access control (protected routes & APIs)
- Full CRUD for all entities (Students, Faculty, HODs, Departments, Subjects, Classes)
- Faculty-Subject-Class assignment management
- Attendance marking with Present/Absent toggles
- Mark All Present / Reset functionality
- Duplicate attendance prevention (same student + subject + date)
- Attendance percentage calculation (backend)
- Attendance status: GOOD (≥85%), WARNING (75-84.99%), CRITICAL (<75%)
- Low attendance monitoring with adjustable threshold
- Search and filter on all management pages
- Subject-wise and overall attendance reports
- Print-friendly report pages
- Responsive design (desktop, tablet, mobile)
- Modern dark theme UI with glassmorphism

## Technology Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React.js 18, Vite 5, React Router 6 |
| UI | Custom CSS (Dark Theme), Lucide React Icons |
| HTTP Client | Axios with interceptors |
| Backend | Java 17, Spring Boot 3.2.5, Maven |
| Security | Spring Security, JWT (jjwt 0.12.5), BCrypt |
| ORM | Spring Data JPA, Hibernate |
| Database | MySQL 8+ |
| API | REST API |

## Architecture

```
Frontend (React + Vite) → REST API → Spring Boot Backend → JPA/Hibernate → MySQL
```

## Project Structure

```
student-attendance-management-system/
├── database/
│   ├── schema.sql          # Database schema
│   └── seed.sql            # Demo data with BCrypt passwords
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/sams/
│       ├── SamsApplication.java
│       ├── config/          # Security config
│       ├── controller/      # REST controllers
│       ├── dto/             # Data transfer objects
│       ├── entity/          # JPA entities
│       ├── exception/       # Global exception handling
│       ├── repository/      # Spring Data repositories
│       ├── security/        # JWT & UserDetailsService
│       └── service/         # Business logic
├── frontend/
│   ├── package.json
│   ├── vite.config.js
│   └── src/
│       ├── App.jsx          # Routes
│       ├── main.jsx         # Entry
│       ├── index.css        # Design system
│       ├── components/      # Reusable components
│       ├── layouts/         # Dashboard layout
│       ├── pages/           # Role-based pages
│       └── services/        # API services
└── README.md
```

## Setup Instructions

### Prerequisites
- Java 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8+

### 1. Database Setup

```bash
# Login to MySQL
mysql -u root -p

# Run schema
source database/schema.sql

# Load demo data
source database/seed.sql
```

### 2. Backend Setup

```bash
cd backend

# Update database credentials in:
# src/main/resources/application.properties
# spring.datasource.username=root
# spring.datasource.password=your_password

# Build and run
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 3. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:5173`

## Demo Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | Admin@123 |
| HOD | hod | Hod@123 |
| Faculty | faculty | Faculty@123 |
| Student | student | Student@123 |

## API Overview

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/auth/login` | POST | User login |
| `/api/dashboard/stats` | GET | Dashboard statistics |
| `/api/students` | GET/POST | List/Create students |
| `/api/students/{id}` | GET/PUT/DELETE | Student CRUD |
| `/api/faculty` | GET/POST | List/Create faculty |
| `/api/faculty/{id}` | GET/PUT/DELETE | Faculty CRUD |
| `/api/hod` | GET/POST | List/Create HODs |
| `/api/hod/{id}` | GET/PUT/DELETE | HOD CRUD |
| `/api/departments` | GET/POST | List/Create departments |
| `/api/departments/{id}` | GET/PUT/DELETE | Department CRUD |
| `/api/subjects` | GET/POST | List/Create subjects |
| `/api/subjects/{id}` | GET/PUT/DELETE | Subject CRUD |
| `/api/classes` | GET/POST | List/Create classes |
| `/api/classes/{id}` | GET/PUT/DELETE | Class CRUD |
| `/api/faculty-subjects` | GET/POST | Faculty assignments |
| `/api/faculty-subjects/{id}` | DELETE | Remove assignment |
| `/api/attendance` | GET/POST | List/Mark attendance |
| `/api/attendance/{id}` | PUT | Update attendance |
| `/api/reports/student/{id}` | GET | Student report |
| `/api/reports/subject/{id}` | GET | Subject report |
| `/api/reports/low-attendance` | GET | Low attendance report |
| `/api/reports/department/{id}` | GET | Department report |

## Environment Variables

### Backend (`application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_attendance_db
spring.datasource.username=root
spring.datasource.password=root
jwt.secret=your-secret-key
jwt.expiration=86400000
```

### Frontend (Vite proxy in `vite.config.js`)
```javascript
proxy: { '/api': { target: 'http://localhost:8080' } }
```

## Testing

- Backend compilation: `mvn compile`
- Frontend build: `npm run build`
- Login with each role to verify access
- Test CRUD operations on all management pages
- Test attendance marking workflow
- Verify attendance calculations match expected values
- Check that role-based access prevents unauthorized access

## Troubleshooting

| Issue | Solution |
|-------|---------|
| Database connection error | Check MySQL is running and credentials match |
| CORS errors | Ensure backend CORS config includes frontend URL |
| JWT expired | Login again to get new token |
| 403 Forbidden | Check user role matches required endpoint role |
| Build errors | Run `mvn clean install` or `npm install` |

## Future Enhancements

- Email notifications for low attendance
- PDF/Excel report export
- Timetable integration
- Parent portal
- Biometric attendance integration
- Analytics dashboard with charts
- SMS notifications
- Bulk student import via CSV
- Academic calendar integration
- Multi-language support
