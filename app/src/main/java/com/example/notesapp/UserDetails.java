package com.example.notesapp;

public class UserDetails {

    static String name;
    static String email;
    static String password_hash;
    static String contact;

    public UserDetails() {
        UserDetails.name = "name";
        UserDetails.email = "email";
        UserDetails.password_hash = "password_hash";
        UserDetails.contact = "contact";
    }

    public UserDetails(String name, String email, String password_hash, String contact) {
        UserDetails.name = name;
        UserDetails.email = email;
        UserDetails.password_hash = password_hash;
        UserDetails.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
