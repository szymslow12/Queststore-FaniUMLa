package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.Inventory;

import java.util.List;

public interface DAOCodecoolerInterface {

    int getCoolcoins(int id);

    boolean buyArtifact(int idUser, int idArtifact);

    List<Artifact> showArtifacts(int categoryID);

    Inventory getBoughtArtifacts(int codecoolerID);

    List<Quest> getDoneQuests(int codecoolerID);
}
