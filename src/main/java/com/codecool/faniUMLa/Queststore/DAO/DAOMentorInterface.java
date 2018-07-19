package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;

import java.util.List;
import java.util.Map;

public interface DAOMentorInterface {

    void addNewCodecooler();

    void addNewQuest();

    void updateQuest();

    void addNewArtifact();

    void updateArtifact();

    boolean markQuestDone();

    boolean markBoughtArtifact();

    void seeCodecoolersWallet();

    void createQuest(String name, String description, int award, int category);

    void createArtifact(String name, int category, int price, String description);

    Codecooler getCodecooler(int index);

    Quest getQuest(int index);

    Artifact getArtifact(int index);

    List<Codecooler> getAllStudents();

    List<Quest> getAllQuests();

    List<Artifact> getAllArtifacts();

    void deleteStudent(int index);

    void deleteQuest(int index);

    void deleteArtifact(int index);

    void editQuest(Map<String, String> inputs);

    void editArtifact(Map<String, String> inputs);

    void createStudent(List<String> studentData);

    void editStudent(String column_name, String changedWord, Integer idUser);
}
