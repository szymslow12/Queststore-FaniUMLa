package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.UserInputs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOMentorHelper {

    private UserInputs userInputs;

    public DAOMentorHelper() {
        userInputs = new UserInputs();
    }

    public String getAddCodecoolerQuery(PreparedStatement statement, Connection connection) throws SQLException {
        String[] messages = {"Enter codecooler login: ", "Enter codecooler password: ",
                "Enter codecooler first name: ", "Enter codecooler last name: ",
                "Enter codecooler email: ", "Enter codecooler phone number: ", "Enter ID of class: "};
        String query = String.format("%s%s%s", "INSERT INTO users (user_login, user_password, user_access, first_name, last_name, email, ",
                "phone_number)\n", "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')");
        String[] queryValues = getQueryValues(messages);
        addNewUser(statement, connection, String.format(query, queryValues[0], queryValues[1], "CODECOOLER", queryValues[2],
                queryValues[3], queryValues[4], queryValues[5]));
        return addNewCodecoolerQuery(statement, connection, queryValues[4], queryValues[6]);
    }


    private String addNewCodecoolerQuery(PreparedStatement statement, Connection connection,
                                         String email, String classID) throws SQLException {

        int userID = getUserID(statement, connection, email);
        return String.format("INSERT INTO codecoolers (id_user, coolcoins," +
                " id_level, id_class)\n VALUES (%s, 0, 1, %s)", userID, classID);
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
        return "SELECT first_name || last_name AS full_name, coolcoins FROM codecoolers";
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
        String query = String.format("INSERT INTO artifacts_codecooleres (id_codecooler, id_artifact)%n%s",
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
