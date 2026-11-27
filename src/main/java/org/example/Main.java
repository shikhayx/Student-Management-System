
package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static String url = "jdbc:mysql://localhost:3306/student_management";
    static String user = "root";
    static String password = "Yadavshikha@21";

    static Connection con() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // REGISTER
    static void register() throws Exception {
        try (Connection c = con();
             PreparedStatement p = c.prepareStatement(
                     "INSERT INTO users(name,email,password) VALUES(?,?,?)")) {

            System.out.print("Name: ");
            p.setString(1, sc.nextLine());

            System.out.print("Email: ");
            p.setString(2, sc.nextLine());

            System.out.print("Password: ");
            p.setString(3, sc.nextLine());

            p.executeUpdate();
            System.out.println("Registration Successful!");
        }
    }

    // LOGIN
    static boolean login() throws Exception {
        try (Connection c = con();
             PreparedStatement p = c.prepareStatement(
                     "SELECT * FROM users WHERE email=? AND password=?")) {

            System.out.print("Email: ");
            p.setString(1, sc.nextLine());

            System.out.print("Password: ");
            p.setString(2, sc.nextLine());

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    System.out.println("Login Successful!");
                    return true;
                }
            }

            System.out.println("Invalid Email or Password!");
            return false;
        }
    }

    // ADD STUDENT
    static void add() throws Exception {
        String sql = "INSERT INTO students(name,email,phone,course,age,city) VALUES(?,?,?,?,?,?)";

        try (Connection c = con();
             PreparedStatement p = c.prepareStatement(sql)) {

            System.out.print("Name: ");
            p.setString(1, sc.nextLine());

            System.out.print("Email: ");
            p.setString(2, sc.nextLine());

            System.out.print("Phone: ");
            p.setString(3, sc.nextLine());

            System.out.print("Course: ");
            p.setString(4, sc.nextLine());

            System.out.print("Age: ");
            p.setInt(5, Integer.parseInt(sc.nextLine()));

            System.out.print("City: ");
            p.setString(6, sc.nextLine());

            p.executeUpdate();
            System.out.println("Student Added!");
        }
    }

    // VIEW STUDENTS
    static void view() throws Exception {
        try (Connection c = con();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery("SELECT * FROM students")) {

            while (r.next()) {
                System.out.println(
                        r.getInt(1) + " | " +
                                r.getString(2) + " | " +
                                r.getString(3) + " | " +
                                r.getString(4) + " | " +
                                r.getString(5) + " | " +
                                r.getInt(6) + " | " +
                                r.getString(7));
            }
        }
    }

    // SEARCH STUDENT
    static void search() throws Exception {
        try (Connection c = con();
             PreparedStatement p = c.prepareStatement(
                     "SELECT * FROM students WHERE student_id=?")) {

            System.out.print("Student ID: ");
            p.setInt(1, Integer.parseInt(sc.nextLine()));

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    for (int i = 1; i <= 7; i++)
                        System.out.println(r.getString(i));
                } else {
                    System.out.println("Student Not Found!");
                }
            }
        }
    }

    // UPDATE STUDENT
    static void update() throws Exception {
        String sql = "UPDATE students SET name=?,email=?,phone=?,course=?,age=?,city=? WHERE student_id=?";

        try (Connection c = con();
             PreparedStatement p = c.prepareStatement(sql)) {

            System.out.print("Student ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("New Name: ");
            p.setString(1, sc.nextLine());

            System.out.print("New Email: ");
            p.setString(2, sc.nextLine());

            System.out.print("New Phone: ");
            p.setString(3, sc.nextLine());

            System.out.print("New Course: ");
            p.setString(4, sc.nextLine());

            System.out.print("New Age: ");
            p.setInt(5, Integer.parseInt(sc.nextLine()));

            System.out.print("New City: ");
            p.setString(6, sc.nextLine());

            p.setInt(7, id);

            int rows = p.executeUpdate();
            System.out.println(rows > 0 ? "Updated!" : "Student Not Found!");
        }
    }

    // DELETE STUDENT
    static void delete() throws Exception {
        try (Connection c = con();
             PreparedStatement p = c.prepareStatement(
                     "DELETE FROM students WHERE student_id=?")) {

            System.out.print("Student ID: ");
            p.setInt(1, Integer.parseInt(sc.nextLine()));

            System.out.print("Confirm (yes/no): ");

            if (sc.nextLine().equalsIgnoreCase("yes")) {
                int rows = p.executeUpdate();
                System.out.println(rows > 0 ? "Deleted!" : "Student Not Found!");
            } else {
                System.out.println("Cancelled!");
            }
        }
    }

    // MAIN MENU
    public static void main(String[] args) {

        try {
            while (true) {
                System.out.println("\n1. Register  2. Login  3. Exit");
                System.out.print("Enter Choice: ");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    register();
                } else if (choice == 2) {
                    if (login()) break;
                } else if (choice == 3) {
                    System.out.println("Thank You!");
                    return;
                } else {
                    System.out.println("Invalid Choice!");
                }
            }

            while (true) {
                System.out.println("\n1.Add  2.View  3.Search  4.Update  5.Delete  6.Exit");
                System.out.print("Enter Choice: ");

                int ch = Integer.parseInt(sc.nextLine());

                switch (ch) {
                    case 1: add(); break;
                    case 2: view(); break;
                    case 3: search(); break;
                    case 4: update(); break;
                    case 5: delete(); break;
                    case 6:
                        System.out.println("Thank You!");
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
