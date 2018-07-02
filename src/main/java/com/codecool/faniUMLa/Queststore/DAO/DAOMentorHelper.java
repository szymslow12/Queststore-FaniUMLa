package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.UserInputs;
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

    public DAOMentorHelper() {
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

    private void addCodecoolerToDatabase(List<String> userData, Connection connection) throws SQLException{
        String email = userData.get(4);
        String idClass= userData.get(userData.size() - 1);
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


    private int getUserID(PreparedStatement statement, Connection connection, String email) throws SQLException {
        statement = connection.prepareStatement(String.format("SELECT id_user, email FROM users WHERE email LIKE '%s'",
                email));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id_user");
    }

    public void addNewUser(PreparedStatement statement, Connection connection, String preparedQuery) throws SQLException {
        statement = connection.prepareStatement(preparedQuery);
        statement.execute();
    }


    public String getAddNewQuestQuery() {
        String[] messages = {"Enter quest category ID: ", "Enter quest name: ",
                "Enter amount of coolcoins for quest: ", "Enter description: "};
        String query = String.format("%s%s", "INSERT INTO quests (id_category, quest_name, award, description)\n",
                "VALUES (%s, '%s', %s, '%s')");
        String[] queryValues = getQueryValues(messages);
        return String.format(query, queryValues[0], queryValues[1], queryValues[2], queryValues[3]);
    }


    public String getAddNewArtifactQuery() {
        String[] messages = {"Enter artifact name: ", "Enter artifact categoryID: ",
                "Enter price: ", "Enter description: "};
        String query = String.format("%s%s", "INSERT INTO artifacts (artifact_name, category_id, price, description)\n",
                "VALUES ('%s', %s, %s, '%s')");
        String[] queryValues = getQueryValues(messages);
        return String.format(query, queryValues[0], queryValues[1], queryValues[2], queryValues[3]);
    }


    public String getCodecoolersWalletsQuery() {
        return String.format("%s%s%s", "SELECT coolcoins",
                ", users.first_name || ' ' || users.last_name AS full_name FROM ",
                "codecoolers\nLEFT JOIN users\nON codecoolers.id_user = users.id_user");
    }


    public String addCoolcoinsToWalletQuery(Connection connection, Integer questID, Integer codecoolerID)
            throws SQLException {

        int award = getAward(connection, questID);
        String query = String.format("UPDATE codecoolers%nSET coolcoins = (coolcoins + %s)%n%s", award,
                String.format("WHERE codecoolers.id_codecooler = %s%n", codecoolerID));
        return query;
    }


    private int getAward(Connection connection, Integer questID) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(
                String.format("SELECT award FROM quests WHERE quests.id_quest = %s", questID));
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("award");
    }


    public Object[] markQuestDoneQueryValues() {
        int[] questAndCodecoolerID = getIntQueryValues(new String[]{"Enter quest ID: ", "Enter codecoolers ID: "});
        String query = String.format("INSERT INTO quests_codecoolers (id_quest, id_codecooler)%n%s",
                String.format("VALUES (%s, %s)", questAndCodecoolerID[0], questAndCodecoolerID[1]));
        Object[] queryValues = new Object[] {query, questAndCodecoolerID[0], questAndCodecoolerID[1]};
        return queryValues;
    }


    public String markBoughtArtifactQuery() {
        int[] queryValues = getIntQueryValues(new String[]{"Enter artifactID: ", "Enter codecoolersID: "});
        String query = String.format("INSERT INTO artifacts_codecoolers (id_codecooler, id_artifact)%n%s",
                String.format("VALUES (%s, %s)", queryValues[1], queryValues[0]));
        return query;
    }


    public String getUpdateQuestQuery() {
        String query = "UPDATE quests SET %s = %s%nWHERE quests.id_quest = %s";
        String column = userInputs.getString("Enter which column do you want update\n" +
                "(cateogry (c)/ name (n)/ award (a)/ description (d)): ");
        int questID = userInputs.getInt("Enter questID which you want update: ");
        return getUpdateQuery(column, query, questID, true);
    }


    private String getUpdateQuery(String column, String query, int recordID, boolean isQuest) {

        String[] columns = new String[]{"c", "category", "n", "name", "a", "award",
                                        "d", "description", "p", "price"};
        for (int i = 0; i < columns.length; i++) {
            if (column.equalsIgnoreCase(columns[i])) {
                if (isQuest)
                    return settedUpdateQuestQuery(query, column, recordID);
                else
                    return settedUpdateArtifactQuery(query, column, recordID);
            }
        }
        return "";
    }


    private String settedUpdateQuestQuery(String query, String column, int questID) {

        if (column.equalsIgnoreCase("n") || column.equalsIgnoreCase("name")) {

            return setUpdateQuery("Enter new quest name: ", query, "quest_name",
                    questID, true);

        } else if (column.equalsIgnoreCase("d") || column.equalsIgnoreCase("description")){

            return setUpdateQuery("Enter new quest description: ", query, "description",
                    questID, true);

        } else if (column.equalsIgnoreCase("c") || column.equalsIgnoreCase("category")) {

            return setUpdateQuery("Enter new quest categoryID: ", query, "id_category",
                    questID, false);

        } else if (column.equalsIgnoreCase("a") || column.equalsIgnoreCase("award")) {

            return setUpdateQuery("Enter new quest award: ", query, "award",
                    questID, false);
        }
        return "";
    }


    private String setUpdateQuery(String message, String query, String columnName, int recordID, boolean isValueString) {
        if (isValueString) {
            String valueToSet = "'" + userInputs.getString(message) + "'";
            return String.format(query, columnName, valueToSet, recordID);
        } else {
            int valueToSet = userInputs.getInt(message);
            return String.format(query, columnName, valueToSet, recordID);
        }
    }


    public String getUpdateArtifactQuery() {
        String query = "UPDATE artifacts SET %s = %s%nWHERE artifacts.id_artifact = %s";
        String column = userInputs.getString("Enter which column do you want update\n" +
                "(cateogry (c)/ name (n)/ price (p)/ description (d)): ");
        int artifactID = userInputs.getInt("Enter artifactID which you want update: ");
        return getUpdateQuery(column, query, artifactID, false);
    }


    private String settedUpdateArtifactQuery(String query, String column, int artifactID) {

        if (column.equalsIgnoreCase("n") || column.equalsIgnoreCase("name")) {

            return setUpdateQuery("Enter new artifact name: ", query, "artifact_name",
                    artifactID, true);

        } else if (column.equalsIgnoreCase("d") || column.equalsIgnoreCase("description")){

            return setUpdateQuery("Enter new artifact description: ", query, "description",
                    artifactID, true);

        } else if (column.equalsIgnoreCase("c") || column.equalsIgnoreCase("category")) {

            return setUpdateQuery("Enter new artifact category: ", query, "category_id",
                    artifactID, false);

        } else if (column.equalsIgnoreCase("p") || column.equalsIgnoreCase("price")) {

            return setUpdateQuery("Enter new artifact price: ", query, "price",
                                artifactID, false);
        }
        return "";
    }


    private String[] getQueryValues(String[] messages) {
        String[] values = new String[messages.length];
        for (int i = 0; i < messages.length; i++) {
            values[i] = userInputs.getString(messages[i]);
        }
        return values;
    }


    private int[] getIntQueryValues(String[] messages) {
        int[] values = new int[messages.length];
        for (int i = 0; i < messages.length; i++) {
            values[i] = userInputs.getInt(messages[i]);
        }
        return values;
    }
}
