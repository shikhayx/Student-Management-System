CREATE DATABASE IF NOT EXISTS student_management;

USE student_management;

CREATE TABLE IF NOT EXISTS students (
                                        student_id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    course VARCHAR(100),
    age INT,
    city VARCHAR(100)
    );

INSERT INTO students
(name, email, phone, course, age, city)
VALUES
    ('Shikha', 'shikha@gmail.com', '9876543210', 'Computer Engineering', 20, 'Mumbai'),
    ('siya', 'siya@gmail.com', '9876543211', 'Information Technology', 21, 'Thane'),
    ('Priya', 'priya@gmail.com', '9876543212', 'Computer Engineering', 20, 'Pune');

SELECT * FROM students;