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
            /*PreparedStatement statement = connection.prepareStatement(helper.getAddNewQuestQuery());
            statement.execute();*/
            helper.addQuest(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateQuest() {
        try {
            /*PreparedStatement statement = connection.prepareStatement(helper.getUpdateQuestQuery());
            statement.execute();*/
            helper.updateRow(connection, "quests");
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void addNewArtifact() {
        try {
            /*PreparedStatement statement = connection.prepareStatement(helper.getAddNewArtifactQuery());
            statement.execute();*/
            helper.addArtifact(connection);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateArtifact() {
        try {
            /*PreparedStatement statement = connection.prepareStatement(helper.getUpdateArtifactQuery());
            statement.execute();*/
            helper.updateRow(connection, "artifacts");
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public boolean markQuestDone() {
        try {
            Object[] queryValues = helper.markQuestDoneQueryValues();
            PreparedStatement statement = connection.prepareStatement((String) queryValues[0]);
            statement.execute();
            addCoolcoinsToCodecoolerWallet(statement, (Integer) queryValues[1], (Integer) queryValues[2]);
            return true;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
    }


    private void addCoolcoinsToCodecoolerWallet(PreparedStatement statement, Integer questID, Integer codecoolerID)
            throws SQLException {

        statement = connection.prepareStatement(helper.addCoolcoinsToWalletQuery(connection, questID, codecoolerID));
        statement.execute();
    }

    public boolean markBoughtArtifact() {
        try {
            PreparedStatement statement = connection.prepareStatement(helper.markBoughtArtifactQuery());
            statement.execute();
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
