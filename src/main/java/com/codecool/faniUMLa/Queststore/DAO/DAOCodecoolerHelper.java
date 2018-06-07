package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.ArtifactCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCodecoolerHelper {
    private View view = new View();
    private Connection connection;
    private final String GET_COOLCOINS = "SELECT coolcoins FROM codecoolers WHERE id_user = ?";
    private final String GET_LEVEL = "SELECT level_of_exp FROM codecoolers WHERE id_user = ?";
    private final String GET_ARTIFACTS = "SELECT * FROM artifacts";

    public DAOCodecoolerHelper(Connection connection) {
        this.connection = connection;
    }

    public int getCoolcoins(int idUser) {
        int coolcoins = 0;
        ResultSet rs = null;
        try {
            rs = coolcoinsQuery(idUser).executeQuery();
            while (rs.next()) {
                coolcoins = rs.getInt("coolcoins");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coolcoins;
    }

    private PreparedStatement coolcoinsQuery(int idUser) throws SQLException {
        PreparedStatement query = null;

        query = connection.prepareStatement(GET_COOLCOINS);
        query.setInt(1, idUser);

        return query;
    }

    public String getLvlOfExp(int idUser) {
        String level = null;
        ResultSet rs = null;
        try {
            rs = levelQuery(idUser).executeQuery();
            while (rs.next()) {
                level = rs.getString("level_of_exp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }

    private PreparedStatement levelQuery(int idUser) throws SQLException {
        PreparedStatement query = null;

        query = connection.prepareStatement(GET_LEVEL);
        query.setInt(1, idUser);

        return query;
    }

    public void buyArtifact(int idUser) {

        List<Artifact> artifacts = getArtifacts();
        view.displayList(artifacts, "Welcome in a shop!");

    }

    private List<Artifact> getArtifacts() {
        List<Artifact> artifacts = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_ARTIFACTS);

            while (rs.next()) {
                int id = rs.getInt("id_artifact");
                String name = rs.getString("artifact_name");
                int category_id = rs.getInt("category_id");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                artifacts.add(new Artifact(id, name, new ArtifactCategory(category_id), price, description))
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artifacts;
    }

    private Statement artifactsQuery() {
        Statement query = null;


    }
}
