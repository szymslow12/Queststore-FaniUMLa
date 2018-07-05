package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.QuestCategory;
import com.codecool.faniUMLa.Queststore.model.UserInputs;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.ArtifactCategory;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import org.postgresql.core.SqlCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMentorHelper {

    private UserInputs userInputs;
    private final String ADD_USER = "INSERT INTO users (user_login, user_password," +
            " first_name, last_name, email, phone_number, user_access) " +
            "VALUES(?,?,?,?,?,?,?)";
    private final String ADD_CODECOOLER = "INSERT INTO codecoolers (id_user, coolcoins, id_level, id_class)" +
            "VALUES (?, ?, ?, ?)";
    private final String GET_USER_ID = "SELECT id_user FROM users WHERE email LIKE ?";
    private final String GET_QUEST_CATEGORY = "SELECT * FROM questcategories";
    private final String ADD_QUEST = "INSERT INTO quests(id_category, quest_name, award, description)" +
            "VALUES(?, ?, ?, ?)";
    private final String GET_ARTIFACT_CATGORY = "SELECT * FROM artcategories";
    private final String ADD_ARTIFACT = "INSERT INTO artifacts(artifact_name, category_id, price, description)" +
            "VALUES(?, ?, ?, ?)";
    private final String GET_ALL_QUESTS = "SELECT * FROM quests";
    private final String GET_ALL_ARTIFACTS = "SELECT * FROM artifacts";
    private final String UPDATE_QUEST = "UPDATE quests SET %s = ? WHERE id_quest = ?";
    private final String UPDATE_ARTIFACT = "UPDATE artifacts SET %s = ? WHERE id_artifact = ?";
    private final String MARK_QUEST_DONE = "INSERT INTO quests_codecoolers (id_quest, id_codecooler) VALUES(?, ?)";
    private final String GET_CODECOOLERS = "SELECT users.id_user, id_codecooler, first_name, last_name, coolcoins, id_level, id_class FROM users \n" +
            "\t JOIN codecoolers ON users.id_user = codecoolers.id_user\n " +
            "\t\tORDER BY users.id_user";
    private final String GET_QUEST_AWARD = "SELECT award FROM quests WHERE id_quest = ?";
    private final String ADD_COOLCOINS_TO_WALLET = "UPDATE codecoolers SET coolcoins = coolcoins + ?" +
            "WHERE id_codecooler = ?";
    private final String MARK_BOUGHT_ARTIFACT = "INSERT INTO artifacts_codecoolers (id_artifact, id_codecooler)" +
            "VALUES(?, ?)";
    private Connection connection;

    public DAOMentorHelper(Connection connection) {
        this.connection = connection;
        userInputs = new UserInputs();
    }


    public void addCodecooler(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_USER);
        List<String> userData = getData(new String[]{"Enter codecooler login: ", "Enter codecooler password: ",
                "Enter codecooler first name: ", "Enter codecooler last name: ",
                "Enter codecooler email: ", "Enter codecooler phone number: ", "Enter ID of class: "});
        for (int i = 0; i < userData.size() - 1; i++) {
            statement.setString(i + 1, userData.get(i));
            System.out.println(userData.get(i));
        }
        statement.setString(userData.size(), "CODECOOLER");
        statement.executeUpdate();
        addCodecoolerToDatabase(userData, connection);

    }


    private void addCodecoolerToDatabase(List<String> userData, Connection connection) throws SQLException {
        String email = userData.get(4);
        String idClass = userData.get(userData.size() - 1);
        PreparedStatement statement = connection.prepareStatement(GET_USER_ID);
        int idUser = getUserID(statement, email);
        statement = connection.prepareStatement(ADD_CODECOOLER);
        statement.setInt(1, idUser);
        statement.setInt(2, 0);
        statement.setInt(3, 1);
        statement.setInt(4, Integer.parseInt(idClass));
        statement.executeUpdate();
    }


    private List<String> getData(String[] labels) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            data.add(userInputs.getString(labels[i]));
        }
        return data;
    }


    private int getUserID(PreparedStatement statement, String email) throws SQLException {
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int idUser = resultSet.getInt("id_user");
        return idUser;
    }


    public void addQuest(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_QUEST_CATEGORY);
        showCategories(statement);
        List<String> questData = getData(new String[]{"Enter quest category ID: ", "Enter quest name: ",
                "Enter amount of award: ", "Enter description: "});
        statement = connection.prepareStatement(ADD_QUEST);
        statement.setInt(1, Integer.parseInt(questData.get(0)));
        statement.setString(2, questData.get(1));
        statement.setInt(3, Integer.parseInt(questData.get(2)));
        statement.setString(4, questData.get(3));
        statement.executeUpdate();

    }


    private void showCategories(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            new View().printLine("--> " + String.valueOf(resultSet.getInt(1)) +
                    ". " + resultSet.getString(2));
        }
    }


    public void addArtifact(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_ARTIFACT_CATGORY);
        showCategories(statement);
        List<String> artifactData = getData(new String[]{"Enter artifact categoryID: ", "Enter artifact name: ",
                "Enter price: ", "Enter description: "});
        statement = connection.prepareStatement(ADD_ARTIFACT);
        statement.setString(1, artifactData.get(1));
        statement.setInt(2, Integer.parseInt(artifactData.get(0)));
        statement.setInt(3, Integer.parseInt(artifactData.get(2)));
        statement.setString(4, artifactData.get(3));
        statement.executeUpdate();

    }


    public void updateRow(Connection connection, String tableName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getAllQuery(tableName));
        printAll(statement, tableName);
        int ID = userInputs.getInt("Enter number: ");
        String columnName = columnName(tableName, getData(getUpdateColumn(tableName)).get(0));
        statement = connection.prepareStatement(String.format(getUpdateQuery(tableName), columnName));
        setUpdateStatement(statement, columnName, ID);
        statement.executeUpdate();

    }


    private String getAllQuery(String tableName) {
        if (tableName.equalsIgnoreCase("quests")) {
            return GET_ALL_QUESTS;
        } else {
            return GET_ALL_ARTIFACTS;
        }
    }


    private String getUpdateQuery(String tableName) {
        if (tableName.equalsIgnoreCase("quests")) {
            return UPDATE_QUEST;
        } else {
            return UPDATE_ARTIFACT;
        }
    }


    private void printAll(PreparedStatement statement, String tableName) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        if (tableName.equalsIgnoreCase("quests")) {
            new View().displayList(getAllQuests(), "");
        } else if (tableName.equalsIgnoreCase("artifacts")) {
            new View().displayList(getAllArtifacts(), "");
        } else {
            new View().displayList(getAllCodecoolers(), "");
        }


    }


    public List<Quest> getAllQuests() throws SQLException {
        ResultSet resultSet = connection.prepareStatement(GET_ALL_QUESTS).executeQuery();
        List<Quest> objectList = new ArrayList<>();
        while (resultSet.next()) {
            int ID = resultSet.getInt(1);
            int cateogryID = resultSet.getInt(2);
            String name = resultSet.getString(3);
            int award = resultSet.getInt(4);
            String description = resultSet.getString(5);
            objectList.add(new Quest(ID, new QuestCategory(cateogryID), name, award, description));
        }
        return objectList;
    }


    public List<Artifact> getAllArtifacts() throws SQLException {
        ResultSet resultSet = connection.prepareStatement(GET_ALL_ARTIFACTS).executeQuery();
        List<Artifact> objectList = new ArrayList<>();
        while (resultSet.next()) {
            int ID = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int categoryID = resultSet.getInt(3);
            int price = resultSet.getInt(4);
            String description = resultSet.getString(5);
            objectList.add(new Artifact(ID, name, new ArtifactCategory(categoryID), price, description));
        }
        return objectList;
    }


    public List<Codecooler> getAllCodecoolers() throws SQLException {
        ResultSet resultSet = connection.prepareStatement(GET_CODECOOLERS).executeQuery();
        List<Codecooler> objectList = new ArrayList<>();
        while (resultSet.next()) {
            int userID = resultSet.getInt(1);
            int codecoolerID = resultSet.getInt(2);
            String firstName = resultSet.getString(3);
            String lastName = resultSet.getString(4);
            int coolcoins = resultSet.getInt(5);
            int idLevel = resultSet.getInt(6);
            int idClass = resultSet.getInt(7);
            objectList.add(new Codecooler(userID, firstName, lastName, idLevel, coolcoins, codecoolerID, idClass));
        }
        return objectList;
    }


    private String[] getUpdateColumn(String tableName) {
        if (tableName.equalsIgnoreCase("quests")) {
            return new String[]{"Enter which column do you want update\n" +
                    "(cateogry (c)/ name (n)/ award (a)/ description (d)): "};
        } else {
            return new String[]{"Enter which column do you want update\n" +
                    "(cateogry (c)/ name (n)/ price (p)/ description (d)): "};
        }
    }


    private void setUpdateStatement(PreparedStatement statement, String columnName, int ID) throws SQLException {
        String updateValue = getData(getUpdateLabel(columnName)).get(0);
        if (columnName.equalsIgnoreCase("award") ||
                columnName.equalsIgnoreCase("price") ||
                columnName.contains("id")) {
            statement.setInt(1, Integer.parseInt(updateValue));
        } else {
            statement.setString(1, updateValue);
        }
        statement.setInt(2, ID);
    }


    private String[] getUpdateLabel(String columnName) {
        if (columnName.equalsIgnoreCase("award") ||
                columnName.equalsIgnoreCase("price") ||
                columnName.contains("id")) {

            return new String[]{"Enter number: "};
        } else {
            return new String[]{"Enter new value: "};
        }
    }


    public String getCodecoolersWalletsQuery() {
        return String.format("%s%s%s", "SELECT coolcoins",
                ", users.first_name || ' ' || users.last_name AS full_name FROM ",
                "codecoolers\nLEFT JOIN users\nON codecoolers.id_user = users.id_user");
    }


    public void markQuestDone(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_ALL_QUESTS);
        printAll(statement, "quests");
        int questID = Integer.parseInt(getData(new String[]{"Enter quest id: "}).get(0));
        statement = connection.prepareStatement(GET_CODECOOLERS);
        printAll(statement, "codecoolers");
        int codecoolerID = Integer.parseInt(getData(new String[]{"Enter codecooler id: "}).get(0));
        markQuestDone(statement, connection, questID, codecoolerID);
        updateCodecoolerCoolcoins(statement, connection, questID, codecoolerID);
    }


    private void markQuestDone(PreparedStatement statement, Connection connection,
                               int questID, int codecoolerID) throws SQLException {

        statement = connection.prepareStatement(MARK_QUEST_DONE);
        statement.setInt(1, questID);
        statement.setInt(2, codecoolerID);
        statement.executeUpdate();
    }


    private void updateCodecoolerCoolcoins(PreparedStatement statement, Connection connection,
                                           int questID, int codecoolerID) throws SQLException {

        statement = connection.prepareStatement(GET_QUEST_AWARD);
        int award = getAward(statement, questID);
        statement = connection.prepareStatement(ADD_COOLCOINS_TO_WALLET);
        statement.setInt(1, award);
        statement.setInt(2, codecoolerID);
        statement.executeUpdate();
    }


    private int getAward(PreparedStatement statement, Integer questID) throws SQLException {
        statement.setInt(1, questID);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("award");
    }


    public void markBoughtArtifact(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(GET_ALL_ARTIFACTS);
        printAll(statement, "artifacts");
        int artifactID = Integer.parseInt(getData(new String[]{"Enter artifactID: "}).get(0));
        statement = connection.prepareStatement(GET_CODECOOLERS);
        printAll(statement, "codecoolers");
        int codecoolerID = Integer.parseInt(getData(new String[]{"Enter codecoolersID: "}).get(0));
        statement = connection.prepareStatement(MARK_BOUGHT_ARTIFACT);
        statement.setInt(1, artifactID);
        statement.setInt(2, codecoolerID);
        statement.executeUpdate();
    }


    private String columnName(String tableName, String answer) {
        if (tableName.equalsIgnoreCase("quests")) {
            return questColumnName(answer);
        } else {
            return artifactColumnName(answer);
        }
    }


    private String questColumnName(String answer) {

        if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("name")) {

            return "quest_name";


        } else if (answer.equalsIgnoreCase("d") || answer.equalsIgnoreCase("description")) {

            return "description";

        } else if (answer.equalsIgnoreCase("c") || answer.equalsIgnoreCase("category")) {

            return "id_category";

        } else if (answer.equalsIgnoreCase("a") || answer.equalsIgnoreCase("award")) {

            return "award";
        }
        return "";
    }


    private String artifactColumnName(String answer) {

        if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("name")) {

            return "artifact_name";

        } else if (answer.equalsIgnoreCase("d") || answer.equalsIgnoreCase("description")) {

            return "description";

        } else if (answer.equalsIgnoreCase("c") || answer.equalsIgnoreCase("category")) {

            return "category_id";

        } else if (answer.equalsIgnoreCase("p") || answer.equalsIgnoreCase("price")) {

            return "price";
        }
        return "";
    }

}
