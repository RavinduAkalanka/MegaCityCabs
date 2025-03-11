# Mega City Cabs

## Overview
Mega City Cabs is a dynamic cab booking system designed for efficient management of cabs, drivers, staff, and customer bookings. The project follows an MVC architecture and is built using Java EE with JSP, Servlets, and a MySQL database.

## Features
- **User Authentication & Role-Based Access Control**
  - Admin, Staff, and Customer login
  - Secure authentication system
- **Cab & Driver Management**
  - CRUD operations for cabs and drivers
  - Staff can view available cabs
- **Booking Process**
  - Customers can book cabs
  - Staff can manage bookings
- **Billing System**
  - Automated fare calculation
  - Invoice generation
- **Dashboards**
  - Separate dashboards for Admin and Staff
- **Search & Filtering**
  - Booking search functionality
- **Unit Testing**
  - Comprehensive test coverage for all components

## Technologies Used
- **Frontend:** JSP, Bootstrap 5, JavaScript, CSS
- **Backend:** Java EE (Servlets, JSP), MySQL
- **Database:** MySQL
- **Build Tool:** Eclipse (Dynamic Web Project)
- **Version Control:** Git

## Project Structure
```
MegaCityCabs/
│-- src/
│   ├── main/java/com/bms/controllers/
│   ├── main/java/com/bms/dao/
│   ├── main/java/com/bms/dto/
│   ├── main/java/com/bms/models/
│   ├── main/java/com/bms/servlets/
│   ├── webapp/
│       ├── WEB-INF/
│       ├── views/
│       ├── assets/
│-- db/
│   ├── database.sql
│-- README.md
```

## Setup Instructions
1. **Clone the repository:**
   ```sh
   git clone https://github.com/RavinduAkalanka/MegaCityCabs.git
   ```
2. **Import into Eclipse:**
   - Open Eclipse
   - File → Import → Existing Projects into Workspace
3. **Configure Database:**
   - Create a MySQL database
   - Import `database.sql`
   - Update `db.properties` with your database credentials
4. **Run the Application:**
   - Deploy on Apache Tomcat
   - Access via `http://localhost:8080/MegaCityCabs`

## Contribution Workflow
- **Branching Strategy:**
  1. Create a feature branch (`feature/<feature-name>`)
  2. Merge into `development` branch
  3. Once tested, merge into `main`
- **Git Workflow:**
  ```sh
  git checkout -b feature/<feature-name>
  git commit -m "Implemented <feature>"
  git push origin feature/<feature-name>
  ```

## Contributors
- **Ravindu Akalanka** - Developer

## Contact
For any queries, reach out via GitHub Issues or email: `ravinduakalankazoysa@gmail.com`.

