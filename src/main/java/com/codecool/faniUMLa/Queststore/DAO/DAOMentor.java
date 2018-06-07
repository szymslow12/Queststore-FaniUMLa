package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            PreparedStatement statement = connection.prepareStatement(helper.markQuestDoneQuery());
            statement.execute();
            return true;
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return false;
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
