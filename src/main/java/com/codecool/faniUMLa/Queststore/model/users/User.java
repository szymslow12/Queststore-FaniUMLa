package com.codecool.faniUMLa.Queststore.model.users;

public abstract class User {

    private int accountID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String login;
    private String password;
    private UserAccess access;

    public User(int accountID, String firstName, String lastName, String email, String phoneNumber) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UserAccess getAccess() {
        return access;
    }
}
