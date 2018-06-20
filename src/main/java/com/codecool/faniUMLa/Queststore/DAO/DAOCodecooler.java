package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;

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
    public void showArtifacts() {
        helper.showArtifacts();
    }
}
