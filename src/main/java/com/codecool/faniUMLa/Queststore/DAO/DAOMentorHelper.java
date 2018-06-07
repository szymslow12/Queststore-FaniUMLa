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


    public String markQuestDoneQuery() {
        int[] questAndCodecoolerID = getIntQueryValues(new String[]{"Enter quest ID: ", "Enter codecoolers ID: "});
        return String.format("INSERT INTO quests_codecoolers (id_quest, id_codecooler)%n%s",
                String.format("VALUES (%s, %s)", questAndCodecoolerID[0], questAndCodecoolerID[1]));
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

    public static void main(String[] args) {
        DAOMentorHelper h = new DAOMentorHelper();
        System.out.println(h.getAddNewQuestQuery());
    }
}
