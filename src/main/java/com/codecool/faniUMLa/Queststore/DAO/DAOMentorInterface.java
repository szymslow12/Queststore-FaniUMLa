package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;

import java.util.List;

public interface DAOMentorInterface {

    void addNewCodecooler();

    void addNewQuest();

    void updateQuest();

    void addNewArtifact();

    void updateArtifact();

    boolean markQuestDone();

    boolean markBoughtArtifact();

    void seeCodecoolersWallet();

    List<Codecooler> getAllStudents();

    List<Quest> getAllQuests();

    List<Artifact> getAllArtifacts();
}
