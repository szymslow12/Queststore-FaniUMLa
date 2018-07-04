package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.sun.org.apache.bcel.internal.classfile.Code;

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

    Codecooler getCodecooler(int index);

    Quest getQuest(int index);

    Artifact getArtifact(int index);

    List<Codecooler> getAllStudents();

    List<Quest> getAllQuests();

    List<Artifact> getAllArtifacts();

    void deleteStudent(int index);

    void deleteQuest(int index);

    void deleteArtifact(int index);
}
