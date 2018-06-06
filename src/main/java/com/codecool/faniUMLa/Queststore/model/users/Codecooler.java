package com.codecool.faniUMLa.Queststore.model.users;

public class Codecooler extends User {

    private int classID;
    private String experience;
    private int coolcoins;

    public Codecooler(int accountID, String firstName, String lastName, String email, String phoneNumber,
                        int classID, String experience, int coolcoins) {
        super(accountID, firstName, lastName, email, phoneNumber);
        this.classID = classID;
        this.experience = experience;
        this.coolcoins = coolcoins;
    }
}
