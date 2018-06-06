package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    }

    public void updateArtifact() {

    }

    public boolean markQuestDone() {
        return true;
    }

    public boolean markBoughtArtifact() {
        return true;
    }

    public void seeCodecoolersWallet() {

    }
}
