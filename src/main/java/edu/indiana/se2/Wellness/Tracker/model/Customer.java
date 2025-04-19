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
    private String firstName;
    private String lastName;
    private String totpSecret;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer(String username, String password, String emailId, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totpSecret = null;
    }
    public Customer(String username, String password, String emailId, String firstName, String lastName, String totpSecret) {
        this.username = username;
        this.password = password;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totpSecret = totpSecret;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTotpSecret(){
        return totpSecret;
    }
    public void setTotpSecret(String totpSecret){
        this.totpSecret = totpSecret;
    }
}
