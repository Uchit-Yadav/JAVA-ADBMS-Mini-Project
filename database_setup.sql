-- ===================================================
-- Student Examination System - Database Setup Script
-- ===================================================

-- Drop database if exists and create fresh
DROP DATABASE IF EXISTS student_exam_db;
CREATE DATABASE student_exam_db;
USE student_exam_db;

-- ===================================================
-- Table 1: students
-- Stores student information for login and registration
-- ===================================================
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===================================================
-- Table 2: questions
-- Stores exam questions with 4 options
-- ===================================================
CREATE TABLE questions (
    question_id INT PRIMARY KEY AUTO_INCREMENT,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL CHECK (correct_option IN ('A', 'B', 'C', 'D')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ===================================================
-- Table 3: results
-- Stores exam results for each student
-- ===================================================
CREATE TABLE results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    percentage DOUBLE NOT NULL,
    exam_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- ===================================================
-- Insert Sample Questions (for testing)
-- ===================================================
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
('What is the capital of France?', 'London', 'Berlin', 'Paris', 'Madrid', 'C'),
('Which programming language is known as "write once, run anywhere"?', 'Python', 'Java', 'C++', 'JavaScript', 'B'),
('What does SQL stand for?', 'Structured Query Language', 'Simple Question Language', 'System Quality Logic', 'Standard Quick Language', 'A'),
('Which data structure follows LIFO principle?', 'Queue', 'Stack', 'Array', 'Linked List', 'B'),
('What is 5 + 3 * 2?', '16', '11', '13', '10', 'B'),
('Which keyword is used to inherit a class in Java?', 'implements', 'inherits', 'extends', 'uses', 'C'),
('What is the default value of a boolean in Java?', 'true', 'false', 'null', '0', 'B'),
('Which collection does not allow duplicate elements?', 'List', 'Set', 'Map', 'Queue', 'B'),
('What is the size of int in Java?', '2 bytes', '4 bytes', '8 bytes', '16 bytes', 'B'),
('Which operator is used for string concatenation in Java?', '+', '&', '*', 'concat', 'A');

-- ===================================================
-- Insert Sample Student (for testing)
-- Password: student123
-- ===================================================
INSERT INTO students (name, email, password) VALUES
('Test Student', 'test@student.com', 'student123');

-- ===================================================
-- Verify Tables Created Successfully
-- ===================================================
SHOW TABLES;

-- Display sample data
SELECT * FROM questions;
SELECT * FROM students;

