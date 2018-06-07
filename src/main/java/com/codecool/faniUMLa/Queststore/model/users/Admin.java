package com.codecool.faniUMLa.Queststore.model.users;

public class Admin extends User {

    public Admin(int accountID, String firstName, String lastName, String email, String phoneNumber) {
        super(accountID, firstName, lastName, email, phoneNumber);
        super.setAccess(UserAccess.ADMIN);
    }
}
