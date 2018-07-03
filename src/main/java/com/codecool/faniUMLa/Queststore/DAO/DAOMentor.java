package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOMentor implements DAOMentorInterface {

    private Connection connection;
    private DAOMentorHelper helper;

    public DAOMentor(Connection connection) {
        this.connection = connection;
        this.helper = new DAOMentorHelper();
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
