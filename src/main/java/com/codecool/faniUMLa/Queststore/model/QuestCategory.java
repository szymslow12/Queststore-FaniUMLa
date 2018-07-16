package com.codecool.faniUMLa.Queststore.model;

public class QuestCategory {

    private int categoryID;
    private String name;

    public QuestCategory(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return String.valueOf(categoryID);
    }
}
