
package org.example.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String user;

    @Value("${spring.datasource.password}")
    String password;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> d) {
        try (Connection c = DriverManager.getConnection(url, user, password);
             PreparedStatement p = c.prepareStatement(
                     "INSERT INTO users(name,email,password) VALUES(?,?,?)")) {

            p.setString(1, d.get("name"));
            p.setString(2, d.get("email"));
            p.setString(3, encoder.encode(d.get("password")));
            p.executeUpdate();

            return ResponseEntity.ok("Signup Successful");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Signup Failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> d) {
        try (Connection c = DriverManager.getConnection(url, user, password);
             PreparedStatement p = c.prepareStatement(
                     "SELECT password FROM users WHERE email=?")) {

            p.setString(1, d.get("email"));
            ResultSet r = p.executeQuery();

            if (r.next() && encoder.matches(
                    d.get("password"), r.getString("password"))) {
                return ResponseEntity.ok("Login Successful");
            }

            return ResponseEntity.status(401).body("Invalid Login");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login Failed");
        }
    }
}
