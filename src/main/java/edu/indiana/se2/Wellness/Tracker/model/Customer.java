package edu.indiana.se2.Wellness.Tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Customer {
    @Id
    private String username;
    private String password;
    private String emailId;
    private String role;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
        this.role = "USER";
    }
    public Customer(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
        this.role = "USER";
    }

    public Customer() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}
}

