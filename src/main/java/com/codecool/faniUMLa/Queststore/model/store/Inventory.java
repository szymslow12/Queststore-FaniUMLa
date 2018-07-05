package com.codecool.faniUMLa.Queststore.model.store;

import java.util.List;
import java.util.Map;

public class Inventory {

    private List<Artifact> artifacts;
    private Map<Integer, Integer> artifactsQuantity;

    public Inventory(List<Artifact> artifacts, Map<Integer, Integer> artifactsQuantity) {
        this.artifacts = artifacts;
        this.artifactsQuantity = artifactsQuantity;
    }


    public List<Artifact> getArtifacts() {
        return artifacts;
    }


    public int getArtifactQuantity(int artifactID) {
        return artifactsQuantity.get(artifactID);
    }


    public String toString() {
        int numberOfItems = calculateNumOfItems();
        return "Inventory contains " + numberOfItems + " items.";
    }


    private int calculateNumOfItems() {
        int sum = 0;
        for (Integer key: artifactsQuantity.keySet()) {
            sum += artifactsQuantity.get(key);
        }
        return sum;
    }
}
