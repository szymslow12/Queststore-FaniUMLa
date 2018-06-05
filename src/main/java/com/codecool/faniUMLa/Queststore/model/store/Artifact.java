package com.codecool.faniUMLa.Queststore.model.store;

public class Artifact {

    private int artifactID;
    private String name;
    private ArtifactCategory category;
    private int price;
    private String description;

    public Artifact(int artifactID, String name, ArtifactCategory category, int price, String description) {
        this.artifactID = artifactID;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
