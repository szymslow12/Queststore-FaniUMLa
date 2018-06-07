package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.*;

public class DAOMentor implements DAOMentorInterface {

    private Connection connection;
    private DAOMentorHelper helper;

    public DAOMentor(Connection connection) {
        this.connection = connection;
        this.helper = new DAOMentorHelper();
    }


    public void addNewCodecooler() {
        try {
            PreparedStatement statement = connection.prepareStatement(helper.getAddCodecoolerQuery());
            statement.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }


    public void addNewQuest() {
        try {
            PreparedStatement statement = connection.prepareStatement(helper.getAddNewQuestQuery());
            statement.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateQuest() {

    }

    public void addNewArtifact() {
        try {
            PreparedStatement statement = connection.prepareStatement(helper.getAddNewArtifactQuery());
            statement.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void updateArtifact() {

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
        return true;
    }

    public ResultSet seeCodecoolersWallet() {
        ResultSet result = null;
        try {
            PreparedStatement statement = connection.prepareStatement(helper.getCodecoolersWalletsQuery());
            result = statement.executeQuery();
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return result;
    }
}
