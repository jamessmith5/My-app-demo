package com.Nameless.earnmoney.Model;

public class RequestPayment {

    String name;
    String email;
    String number;
    String date;
    int balance;

    public RequestPayment() {
    }

    public RequestPayment(String name, String email, String number, String date, int balance) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.date = date;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}


