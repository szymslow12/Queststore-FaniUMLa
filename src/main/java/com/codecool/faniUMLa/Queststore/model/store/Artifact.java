package com.codecool.faniUMLa.Queststore.model.store;

public class Artifact {

    private int artifactID;
    private String name;
    private int category;
    private int price;
    private String description;

    public Artifact(int id, String name, int category, int price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.artifactID = id;
    }

    public String getName() {
        return name;
    }

    public int getArtifactID() {
        return artifactID;
    }

    public int getCategory() {

        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }


    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(artifactID);
        sb.append(" ");
        sb.append(name);
        sb.append(" ");
        sb.append(category);
        sb.append(" ");
        sb.append(price);
        sb.append(" ");
        sb.append(description);
        return sb.toString();
    }
}
