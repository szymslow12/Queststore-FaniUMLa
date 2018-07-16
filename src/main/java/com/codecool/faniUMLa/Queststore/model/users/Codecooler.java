package com.codecool.faniUMLa.Queststore.model.users;

import netscape.security.Privilege;

public class Codecooler extends User {

    private int classID;
    private int level_id;
    private int coolcoins;
    private int id_codecooler;

    public Codecooler(int idUser, String firstName, String lastName, String email, String phoneNumber, int classID,
                      int level_id, int coolcoins, int id_codecooler) {
        super(idUser, firstName, lastName, email, phoneNumber);
        this.classID = classID;
        this.level_id = level_id;
        this.coolcoins = coolcoins;
        this.id_codecooler = id_codecooler;
        super.setAccess(UserAccess.CODECOOLER);
    }


    public int getClassID() {
        return classID;
    }

    public int getLevel_id() {
        return level_id;
    }

    public int getCoolcoins() {
        return coolcoins;
    }

    public Codecooler(int idUser, String firstName, String lastName, int level_id, int coolcoins, int id_codecooler,
                      int class_id) {
        super(idUser, firstName, lastName);
        this.id_codecooler = id_codecooler;
        this.level_id = level_id;
        this.coolcoins = coolcoins;
        this.classID = class_id;

    }

    public String toString() {
        return super.toString() + " Codecooler ID: " + id_codecooler;
    }
}
