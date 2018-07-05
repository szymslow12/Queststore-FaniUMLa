package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.Inventory;

import java.sql.Connection;
import java.util.List;

public class DAOCodecooler implements DAOCodecoolerInterface {

    private DAOCodecoolerHelper helper;
    private Connection connection;

    public DAOCodecooler(Connection connection) {
        this.connection = connection;
        helper = new DAOCodecoolerHelper(connection);
    }

    @Override
    public int getCoolcoins(int id) {
        return helper.getCoolcoins(id);
    }

    @Override
    public String getLevel(int id) {
        return helper.getLvlOfExp(id);
    }

    @Override
    public void buyArtifact(int idUser, int idArtifact) {
        helper.buyArtifact(idUser, idArtifact);
    }

    @Override
    public List<Artifact> showArtifacts() {
        return helper.showArtifacts();
    }

    @Override
    public Inventory getBoughtArtifacts(int codecoolerID) {
        return helper.getBoughtArtifacts(codecoolerID);
    }

    @Override
    public List<Quest> getDoneQuests(int codecoolerID) {
        return helper.getDoneQuests(codecoolerID);
    }
}
