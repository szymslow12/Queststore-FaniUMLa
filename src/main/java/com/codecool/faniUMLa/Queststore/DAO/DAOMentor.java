package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.sun.org.apache.bcel.internal.classfile.Code;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOMentor implements DAOMentorInterface {

    private Connection connection;
    private DAOMentorHelper helper;

    public DAOMentor(Connection connection) {
        this.connection = connection;
        this.helper = new DAOMentorHelper(connection);
    }

    public List<Codecooler> getAllStudents() {
        List<Codecooler> students = new ArrayList<>();
        try {
            students = helper.getAllCodecoolers();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public Codecooler getCodecooler(int index) {
        List<Codecooler> students = getAllStudents();
        return students.get(index);
    }

    public List<Quest> getAllQuests() {
        List<Quest> quests = new ArrayList<>();
        try {
            quests = helper.getAllQuests();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return quests;
    }

    public Quest getQuest(int index) {
        List<Quest> quests = getAllQuests();
        return quests.get(index);
    }

    public List<Artifact> getAllArtifacts() {
        List<Artifact> artifacts = new ArrayList<>();
        try {
            artifacts = helper.getAllArtifacts();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return artifacts;
    }

    public Artifact getArtifact(int index) {
        List<Artifact> artifacts = getAllArtifacts();
        return artifacts.get(index);
    }


    public void addNewCodecooler() {
        try {
            helper.addCodecooler(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }


    public void addNewQuest() {
        try {
            helper.addQuest(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateQuest() {
        try {
            helper.updateRow(connection, "quests");
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void addNewArtifact() {
        try {
            helper.addArtifact(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateArtifact() {
        try {
            helper.updateRow(connection, "artifacts");
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void deleteStudent(int index) {
        helper.deleteStudent(index);
    }

    public void deleteQuest(int index) {
        helper.deleteQuest(index);
    }

    public void deleteArtifact(int index) {
        helper.deleteArtifact(index);
    }


    public boolean markQuestDone() {
        try {
            helper.markQuestDone(connection);
            return true;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }


    public boolean markBoughtArtifact() {
        try {
            helper.markBoughtArtifact(connection);
            return true;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }

    public void seeCodecoolersWallet() {
        ResultSet result = null;
        List<String> fullLineList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(helper.getCodecoolersWalletsQuery());
            result = statement.executeQuery();
            fullLineList = getFullLineList(result, fullLineList);

        } catch (SQLException err) {
            err.printStackTrace();
        }

        new View().displayList(fullLineList, "Codecoolers wallets: ");
    }


    private List<String> getFullLineList(ResultSet result, List<String> fullLineList) throws SQLException {
        while (!result.isLast()) {
            result.next();
            fullLineList.add(getFullLine(result.getString("full_name"),
                    result.getInt("coolcoins")));
        }
        return fullLineList;
    }


    private String getFullLine(String fullName, int coolcoins) {
        return String.format("%s . %s", fullName, coolcoins);
    }
}
