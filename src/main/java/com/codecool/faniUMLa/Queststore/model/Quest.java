package com.codecool.faniUMLa.Queststore.model;

public class Quest {

    private int questID;
    private QuestCategory category;
    private String name;
    private int award;
    private String description;

    public Quest(int questID, QuestCategory category, String name, int award, String description) {
        this.questID = questID;
        this.category = category;
        this.name = name;
        this.award = award;
        this.description = description;
    }

    public String toString() {
        return questID + ". " + name;
    }

    public int getQuestID() {
        return questID;
    }

    public QuestCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public int getAward() {
        return award;
    }

    public String getDescription() {
        return description;
    }
}
