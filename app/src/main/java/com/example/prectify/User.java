package com.example.prectify;

public class User {
    public String username;
    public String email;
    public String uid;
    public String password;





    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String uid,String password) {
        this.username = username;
        this.email = email;
        this.uid=uid;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public String getuserEmail() {
        return email;
    }

    public String getuserUid() {
        return uid;
    }

    public String getuserPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setuserEmail(String email) {
        this.email = email;
    }

    public void setuserUid(String uid) {
        this.uid = uid;
    }

    public void setuserPassword(String password) {
        this.password = password;
    }
}
