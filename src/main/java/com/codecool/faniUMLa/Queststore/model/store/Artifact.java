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

    public String getName() {
        return name;
    }

    public int getArtifactID() {
        return artifactID;
    }

    public ArtifactCategory getCategory() {

        return category;
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
