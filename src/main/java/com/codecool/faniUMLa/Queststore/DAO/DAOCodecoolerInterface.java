package com.codecool.faniUMLa.Queststore.DAO;

public interface DAOCodecoolerInterface {

    int getCoolcoins(int id);

    String getLevel(int id);

    void buyArtifact(int idUser, int idArtifact);

    void showArtifacts();
}
