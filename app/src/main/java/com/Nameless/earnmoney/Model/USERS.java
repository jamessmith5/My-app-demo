package com.Nameless.earnmoney.Model;

public class USERS {

    private String email;
    private String fullName;
    private String username;
    private String phone;
    private String password;
    int points, rupees;

    public USERS() {
    }

    public USERS(String email, String fullName, String username, String phone, String password, int points, int rupees) {
        this.email = email;
        this.fullName = fullName;
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.points = points;
        this.rupees = rupees;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getRupees() {
        return rupees;
    }

    public void setRupees(int rupees) {
        this.rupees = rupees;
    }
}
