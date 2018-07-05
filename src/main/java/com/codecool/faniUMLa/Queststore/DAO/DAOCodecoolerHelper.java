package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.QuestCategory;
import com.codecool.faniUMLa.Queststore.model.UserInputs;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.ArtifactCategory;
import com.codecool.faniUMLa.Queststore.model.store.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOCodecoolerHelper {

    private UserInputs userInputs;
    private View view;
    private Connection connection;
    private final String GET_COOLCOINS = "SELECT coolcoins FROM codecoolers WHERE id_user = ?;";
    private final String GET_LEVEL = "SELECT id_level FROM codecoolers WHERE id_codecooler = ?;";
    private final String GET_ARTIFACTS = "SELECT * FROM artifacts";
    private final String ADD_ITEM = "INSERT INTO artifacts_codecooleres VALUES (?, ?, ?);";
    private final String GET_GROUP = "SELECT * FROM groups WHERE id_artifact = ?;";
    private final String UPDATE_GROUP = "UPDATE groups SET money = ? WHERE id_group = ?;";
    private final String INSERT_BOUGHT_ITEM = "INSERT INTO groups_codecoolers VALUES (?, ?, ?);";
    private final String INSERT_NEW_GROUP = "INSERT INTO groups(id_artifact, money) VALUES (?, ?);";
    private final String SUBSTRACT_MONEY = "UPDATE codecoolers SET money = ? WHERE id_codecooler = ?;";
    private final String GET_STUDENT_ARTIFACTS = "SELECT * FROM artifacts_codecooleres WHERE id_artifact = ?;";
    private final String UPDATE_STUDENT_ARTIFACTS = "UPDATE artifacts_codecooleres " +
            "SET quantity = ? WHERE id_codecooler = ? AND id_artifact = ?;";
    private final String GET_BOUGHT_STUDENT_ARTIFACTS = "SELECT artifacts.id_artifact, category_id, artifact_name, price, description, quantity FROM artifacts\n" +
            "JOIN artifacts_codecoolers ON artifacts_codecoolers.id_codecooler = ? \n" +
            "AND artifacts.id_artifact = artifacts_codecoolers.id_artifact;";
    private final String GET_DONE_QUESTS = "SELECT quests.id_quest, id_category, quest_name, award, description FROM quests\n" +
            "JOIN quests_codecoolers ON quests_codecoolers.id_codecooler = ?\n" +
            "AND quests.id_quest = quests_codecoolers.id_quest;";

    public DAOCodecoolerHelper(Connection connection) {
        this.view = new View();
        this.connection = connection;
        this.userInputs = new UserInputs();
    }

    public int getCoolcoins(int idUser) {
        int coolcoins = 0;
        ResultSet rs;
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
        PreparedStatement query;

        query = connection.prepareStatement(GET_COOLCOINS);
        query.setInt(1, idUser);
        return query;
    }

    public String getLvlOfExp(int idUser) {
        String level = null;
        ResultSet rs;
        try {
            rs = levelQuery(idUser).executeQuery();
            while (rs.next()) {
                level = rs.getString("id_level");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }

    private PreparedStatement levelQuery(int idUser) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(GET_LEVEL);
        query.setInt(1, idUser);
        return query;
    }

    public void buyArtifact(int idUser, int idArtifact) {
        List<Artifact> artifacts = getArtifacts();
        Artifact artifact = artifacts.get(idArtifact - 1);
        if (isSingleBuyer(artifact)) {
            ResultSet rs;
            try {
                rs = studentArtQuery(idUser).executeQuery();
                if (rs.next()) {
                    if (rs.getInt("id_artifact") != artifact.getCategory().getCategoryID()) {
                        int price = artifact.getPrice();
                        int money = getCoolcoins(idUser);
                        if (isAvailable(price, money)) {
                            addItem(artifact, idUser);

                        } else {

                            view.printLine("You cannot afford this item!");
                        }
                    } else {
                        updateStudentArt(idUser, idArtifact, rs.getInt("quantity")).executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            groupShopping(artifact, idUser);
        }

    }

    private PreparedStatement updateStudentArt(int idUser, int idArtifact, int quantity) throws SQLException{
        PreparedStatement query;
        query = connection.prepareStatement(UPDATE_STUDENT_ARTIFACTS);
        query.setInt(1, quantity + 1);
        query.setInt(2, idUser);
        query.setInt(3, idArtifact);
        return  query;
    }


    private PreparedStatement studentArtQuery (int idUser) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(GET_STUDENT_ARTIFACTS);
        query.setInt(1, idUser);
        return query;
    }

    private boolean isSingleBuyer(Artifact artifact) {
        return artifact.getCategory().getCategoryID() == 1;
    }

    public List<Artifact> showArtifacts() {
        List<Artifact> artifacts = getArtifacts();
        view.displayList(artifacts, "Welcome in a shop!");
        return artifacts;
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
                artifacts.add(new Artifact(id, name, new ArtifactCategory(category_id), price, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artifacts;
    }

    private boolean isAvailable(int price, int money) {
        return price <= money;
    }

    private void addItem(Artifact item, int idUser) {
        try {
            buyQuery(idUser, item.getArtifactID()).executeUpdate();
            substractMoneyQuery(idUser, item.getPrice()).executeUpdate();
            view.printLine("Congratulations! You bought item.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private PreparedStatement substractMoneyQuery(int idUser, int price) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(SUBSTRACT_MONEY);
        query.setInt(1, getCoolcoins(idUser) - price);
        query.setInt(2, idUser);
        return query;
    }

    private PreparedStatement buyQuery(int idUser, int idArtifact) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(ADD_ITEM);
        query.setInt(1, idUser);
        query.setInt(2, idArtifact);
        query.setInt(3, 1);

        return query;
    }

    private void groupShopping(Artifact artifact, int idUser) {
        buyArtifact(artifact, idUser);
    }

    private void buyArtifact(Artifact artifact, int idUser) {

        ResultSet rs;
        try {
            rs = groupQuery(artifact.getArtifactID()).executeQuery();
            view.printLine(String.valueOf(getCoolcoins(idUser)));
            if (rs.next()) {
                view.printLine("You need " + String.valueOf(artifact.getPrice() - rs.getInt("money") + " to buy this item."));
                int coolcoins = getMoney(idUser);
                int deficit = calculateDeficit(artifact, rs);
                int moneyToGive = (coolcoins < deficit) ? coolcoins : deficit;
                int newDeficit = deficit - moneyToGive;
                updateGroup(newDeficit, rs.getInt("id_group")).executeUpdate();

                if (isBought(newDeficit, idUser, rs)) {
                    view.printLine("Congratulations! Your group has just bought a new item!");
                } else {
                    view.printLine("Missing money " + String.valueOf(newDeficit));
                }
            } else {
                int coolcoins = userInputs.getInt("How much money would like to give ?");
                createNewGroup(artifact, coolcoins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int calculateDeficit(Artifact artifact, ResultSet rs) throws SQLException {
        if (artifact.getPrice() > rs.getInt("money")) {
            return artifact.getPrice() - rs.getInt("money");
        } else return artifact.getPrice();
    }

    private int getMoney(int idUser) throws SQLException {

        int money = getCoolcoins(idUser);
        int coolcoins = userInputs.getInt("How much money would like to give ?");
        if (coolcoins > money) {
            view.printLine("You don't have enough money!");
            getMoney(idUser);
        }
        substractMoneyQuery(idUser, money - coolcoins);
        return coolcoins;
    }

    private PreparedStatement groupQuery(int idArtifact) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(GET_GROUP);
        query.setInt(1, idArtifact);
        return query;
    }

    private PreparedStatement updateGroup(int newDeficit, int idGroup) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(UPDATE_GROUP);
        query.setInt(1, newDeficit);
        query.setInt(2, idGroup);
        return query;
    }

    private boolean isBought(int newDeficit, int idUser, ResultSet rs) throws SQLException {
        if (newDeficit == 0) {
            addToNewTable(idUser, rs);
            return true;
        } return false;
    }

    private void addToNewTable(int idUser, ResultSet rs) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(INSERT_BOUGHT_ITEM);
        query.setInt(1, rs.getInt("id_group"));
        query.setInt(2, idUser);
        query.setInt(3, rs.getInt("id_artifact"));
        query.executeUpdate();
    }

    private void createNewGroup(Artifact artifact, int money) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(INSERT_NEW_GROUP);
        query.setInt(1, artifact.getArtifactID());
        query.setInt(2, artifact.getPrice() - money);

        query.executeUpdate();
    }

    public Inventory getBoughtArtifacts(int codecoolerID) {
        List<Artifact> boughtArtifacts = new ArrayList<>();
        Map<Integer, Integer> artifactsQuantity = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_BOUGHT_STUDENT_ARTIFACTS);
            statement.setInt(1, codecoolerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int artifactID = resultSet.getInt("id_artifact");
                int categoryID = resultSet.getInt("category_id");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                String name = resultSet.getString("artifact_name");
                String description = resultSet.getString("description");
                boughtArtifacts.add(new Artifact(artifactID, name, new ArtifactCategory(categoryID),price, description));
                artifactsQuantity.put(artifactID, quantity);
            }

        } catch (SQLException err) {
            err.printStackTrace();
        }
        return new Inventory(boughtArtifacts, artifactsQuantity);
    }


    public List<Quest> getDoneQuests(int codecoolerID) {
        List<Quest> doneQuests = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_DONE_QUESTS);
            statement.setInt(1, codecoolerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int questID = resultSet.getInt("id_quest");
                int categoryID = resultSet.getInt("id_category");
                int award = resultSet.getInt("award");
                String name = resultSet.getString("quest_name");
                String description = resultSet.getString("description");
                doneQuests.add(new Quest(questID, new QuestCategory(categoryID), name, award, description));
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return doneQuests;
    }
}
