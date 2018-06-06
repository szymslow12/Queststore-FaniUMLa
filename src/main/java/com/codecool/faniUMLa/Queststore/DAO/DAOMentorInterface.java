package com.codecool.faniUMLa.Queststore.DAO;

public interface DAOMentorInterface {

    void addNewCodecooler();

    void addNewQuest();

    void updateQuest();

    void addNewArtifact();

    void updateArtifact();

    boolean markQuestDone();

    boolean markBoughtArtifact();

    void seeCodecoolersWallet();
}
