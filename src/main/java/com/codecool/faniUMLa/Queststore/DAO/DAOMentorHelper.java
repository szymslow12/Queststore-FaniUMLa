package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.UserInputs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOMentorHelper {

    private UserInputs userInputs;

    public DAOMentorHelper() {
        userInputs = new UserInputs();
    }

    public String getAddCodecoolerQuery() {
        String[] messages = {"Enter codecooler first name: ", "Enter codecooler last name: ",
                "Enter codecooler email: ", "Enter codecooler phone number: ", "Enter ID of class: "};
        String query = String.format("%s%s%s", "INSERT INTO codecoolers (first_name, last_name, email, ",
                "phone_number, coolcoins, level_of_exp, id_class)\n", "VALUES ('%s', '%s', '%s', '%s', 0, 0, %s)");
        String[] queryValues = getQueryValues(messages);
        return String.format(query, queryValues[0], queryValues[1], queryValues[2], queryValues[3], queryValues[4]);
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
        String[] messages = {"Enter artifact name: ", "Enter artifact category: ",
                "Enter price: ", "Enter description: "};
        String query = String.format("%s%s", "INSERT INTO artifacts (artifact_name, category, price, description)\n",
                "VALUES (%s, '%s', %s, '%s')");
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

        if (column.equalsIgnoreCase("n") || column.equalsIgnoreCase("d") ||
                column.equalsIgnoreCase("name") || column.equalsIgnoreCase("description")) {

            if (column.equalsIgnoreCase("n") || column.equalsIgnoreCase("name")) {
                String name = "'" + userInputs.getString("Enter new quest name: ") + "'";
                return String.format(query, "quest_name", name, questID);
            } else {
                String description = "'" + userInputs.getString("Enter new quest description: ") + "'";
                return String.format(query, "description", description, questID);
            }

        } else if (column.equalsIgnoreCase("c") || column.equalsIgnoreCase("a") ||
                column.equalsIgnoreCase("category") || column.equalsIgnoreCase("award")) {

            if (column.equalsIgnoreCase("c") || column.equalsIgnoreCase("category")) {
                int categoryID = userInputs.getInt("Enter new quest categoryID: ");
                return String.format(query, "id_category", categoryID, questID);
            } else {
                int award = userInputs.getInt("Enter new quest award: ");
                return String.format(query, "award", award, questID);
            }
        }
        return "";
    }


    public String getUpdateArtifactQuery() {
        String query = "UPDATE store SET %s = %s%nWHERE store.id_artifact = %s";
        String column = userInputs.getString("Enter which column do you want update\n" +
                "(cateogry (c)/ name (n)/ price (p)/ description (d)): ");
        int artifactID = userInputs.getInt("Enter artifactID which you want update: ");

        if (column.equalsIgnoreCase("n") || column.equalsIgnoreCase("d") ||
                column.equalsIgnoreCase("name") || column.equalsIgnoreCase("description") ||
                column.equalsIgnoreCase("c") || column.equalsIgnoreCase("cateogry")) {

            if (column.equalsIgnoreCase("n") || column.equalsIgnoreCase("name")) {
                String name = "'" + userInputs.getString("Enter new artifact name: ") + "'";
                return String.format(query, "artifact_name", name, artifactID);
            } else if (column.equalsIgnoreCase("c") || column.equalsIgnoreCase("category")){
                String category = "'" + userInputs.getString("Enter new artifact category: ") + "'";
                return String.format(query, "category", category, artifactID);
            } else {
                String description = "'" + userInputs.getString("Enter new artifact description: ") + "'";
                return String.format(query, "description", description, artifactID);
            }

        } else if (column.equalsIgnoreCase("p") || column.equalsIgnoreCase("price")) {
            int price = userInputs.getInt("Enter new artifact price: ");
            return String.format(query, "price", price, artifactID);
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
