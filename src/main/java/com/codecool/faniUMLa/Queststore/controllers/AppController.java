package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.*;
import com.codecool.faniUMLa.Queststore.model.UserInputs;
import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

import java.util.ArrayList;

public class AppController extends Controller {
    DAOAdminInterface daoAdmin;
    DAOCodecoolerInterface daoCodecooler;
    UserInputs inputs;

    public AppController() {
        daoAdmin = new DAOAdmin(connection);
        daoCodecooler = new DAOCodecooler(connection);
        inputs = new UserInputs();
        signIn();
    }

    public void handleMenu(UserPrivilege privilege) {
        switch (privilege) {
            case CREATE_MENTOR:
                daoAdmin.createMentor(getUserData());
                break;
            case CREATE_CLASS:
                String class_name = askUser("Provide class_name");
                daoAdmin.createClass(class_name);
                break;
            case EDIT_MENTOR:
                view.displayList(daoAdmin.getAllMentors(), "");
                int chosenMentor = (Integer.valueOf(askUser("Which mentor would you like to choose(number) "))-1);
                handleMentorUpdate(daoAdmin.getMentor(chosenMentor).getIdUser());
                break;
            case SEE_MENTOR:
                view.displayList(daoAdmin.getAllMentors(), "");
                int mentorIndex = (Integer.valueOf(askUser("Provide mentor's number"))-1);
                view.printLine(daoAdmin.getMentor(mentorIndex).getMentorDetails());
                break;
            case CREATE_LEVELS:
                String level_name = askUser("Provide name");
                daoAdmin.createLevel(level_name);
                break;
            case SEE_WALLET:
                view.printLine(String.valueOf(daoCodecooler.getCoolcoins(getUser().getIdUser())));
                break;
            case SEE_LEVEL:
                view.printLine(daoCodecooler.getLevel(getUser().getIdUser()));
                break;
            case BUY_ARTIFACT:
                daoCodecooler.showArtifacts();
                int idArtifact = inputs.getInt("Which artifact would you like to buy? ");
                daoCodecooler.buyArtifact(getUser().getIdUser(), idArtifact);
            case EXIT:
                view.printLine("Bye bye");
        }
    }

    public void run() {
        UserPrivilege privilege = null;
        do {
            try{
                privilege = choosePrivilege();
                handleMenu(privilege);
            } catch (NumberFormatException e) {
                view.printLine("Please provide number!");
            }
        }while(isRun(privilege));
    }

    private ArrayList<String> getUserData() {
        ArrayList <String> userData = new ArrayList<>();
        String[] column = {"first name", "last name", "phone number", "email", "login", "password"};
        for(int i = 0; i<column.length; i++) {
            userData.add(askUser("Provide " + column[i] + ": "));
        }
        return userData;
    }

    private void handleMentorUpdate(Integer id_user) {
        String word;
        String answer;
        String[] column = {"first_name", "last_name", "phone_number", "email"};
        for(int i = 0; i < column.length; i++) {
            answer = askUser("Would you like to update " + column[i] + " (y/n)?");
            if(answer.equalsIgnoreCase("y")) {
                word = askUser("Provide " + column[i] + ": ");
                daoAdmin.editMentor(column[i], word, id_user);
            }
        }
    }

    private boolean isRun(UserPrivilege privilege) {
        return privilege != UserPrivilege.EXIT;
    }
}
