package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.UserInputs;

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
        String[] queryValues = getQueryValues(messages.length, messages);
        return String.format(query, queryValues[0], queryValues[1], queryValues[2], queryValues[3], queryValues[4]);
    }


    public String getAddNewQuestQuery() {
        String[] messages = {"Enter quest category ID: ", "Enter quest name: ",
                "Enter amount of coolcoins for quest: ", "Enter description: "};
        String query = String.format("%s%s", "INSERT INTO quests (id_category, quest_name, award, description)\n",
                "VALUES (%s, '%s', %s, '%s')");
        String[] queryValues = getQueryValues(messages.length, messages);
        return String.format(query, queryValues[0], queryValues[1], queryValues[2], queryValues[3]);
    }


    public String getAddNewArtifactQuery() {
        String[] messages = {"Enter artifact name: ", "Enter artifact category: ",
                "Enter price: ", "Enter description: "};
        String query = String.format("%s%s", "INSERT INTO store (artifact_name, category, price, description)\n",
                "VALUES (%s, '%s', %s, '%s')");
        String[] queryValues = getQueryValues(messages.length, messages);
        return String.format(query, queryValues[0], queryValues[1], queryValues[2], queryValues[3]);
    }


    private String[] getQueryValues(int valuesSize, String[] messages) {
        String[] values = new String[valuesSize];
        for (int i = 0; i < valuesSize; i++) {
            values[i] = userInputs.getString(messages[i]);
        }
        return values;
    }

    public static void main(String[] args) {
        DAOMentorHelper h = new DAOMentorHelper();
        System.out.println(h.getAddNewQuestQuery());
    }
}
