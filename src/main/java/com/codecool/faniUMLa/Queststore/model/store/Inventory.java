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
}
