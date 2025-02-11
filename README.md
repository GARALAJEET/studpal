# StudPal - Student Management System

## Overview
StudPal is a Java-based student management system designed to help students manage their schedules, track expenses, and maintain a personal wallet. It includes features for timetable management, authentication, expense tracking, and wallet management, all backed by a MySQL database.

## Features
- **User Authentication**: Sign up and log in functionality.
- **Timetable Management**: Add, update, delete, view, and export timetables.
- **College Timetable**: Manage lecture schedules.
- **Expense Tracking**: Log expenses and categorize spending.
- **Wallet Management**: Add and spend funds with balance tracking.

## Technologies Used
- **Java (Core Java, JDBC)**
- **MySQL Database**
- **Custom Data Structures (MyArrayList)**
- **File Handling (Text File Exports)**

## Project Structure
```
|-- src/
|   |-- AuthService.java  # Handles user authentication and expense tracking
|   |-- DatabaseConnection.java  # Establishes database connection
|   |-- Expense.java  # Represents expense entities
|   |-- Main.java  # Entry point of the application
|   |-- MyArrayList.java  # Custom implementation of ArrayList
|   |-- Timetable.java  # Represents timetable entries
|   |-- TimetableDAO.java  # Handles database operations for college timetables
|   |-- TimetableService.java  # Manages timetable CRUD operations
|   |-- User.java  # Represents a user entity
|   |-- college_Timetable.java  # Represents college timetable entries
|-- README.md  # Project documentation
```

## Setup Instructions
### Prerequisites
- Install **Java (JDK 8 or higher)**
- Install **MySQL** and create a database named `studpal`
- Update database credentials in `DatabaseConnection.java`

### Database Configuration
Create the required tables using the following SQL script:
```sql
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    fullName VARCHAR(100),
    age INT,
    address VARCHAR(255),
    phoneNumber VARCHAR(20),
    panNumber VARCHAR(20),
    currentBalance DOUBLE DEFAULT 0.0
);

CREATE TABLE expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    amount DOUBLE NOT NULL,
    category VARCHAR(50),
    date DATE NOT NULL,
    description TEXT,
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE timetable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    event_title VARCHAR(255),
    dayOfWeek VARCHAR(20),
    location VARCHAR(100),
    description TEXT,
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE college_timetable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    day VARCHAR(20),
    time VARCHAR(50),
    subject VARCHAR(100),
    number_of_lectures INT,
    username VARCHAR(50),
    FOREIGN KEY (username) REFERENCES users(username)
);
```

### Running the Application
1. Compile all Java files:
   ```sh
   javac -d bin src/*.java
   ```
2. Run the application:
   ```sh
   java -cp bin Main
   ```

## Usage Guide
1. **Sign Up or Log In**
2. **Manage Basic Details**
3. **Add and View Expenses**
4. **Manage Wallet Funds**
5. **Create and View Timetables**
6. **Export Timetable or Expense Reports**

## Future Enhancements
- Implement a GUI for better user experience
- Add notification and reminder features for timetable events
- Implement REST APIs for external integration

## Author
Developed by [Your Name]

