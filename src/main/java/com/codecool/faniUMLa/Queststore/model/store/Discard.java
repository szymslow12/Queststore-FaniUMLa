package com.codecool.faniUMLa.Queststore.model.store;

public class Discard {

    private int discardID;
    private String name;
    private int artifactID;
    private int amount;

    public Discard(int discardID, String name, int artifactID, int amount) {
        this.discardID = discardID;
        this.name = name;
        this.artifactID = artifactID;
        this.amount = amount;
    }
}
