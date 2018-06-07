package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOAdmin;
import com.codecool.faniUMLa.Queststore.DAO.DAOAdminInterface;
import com.codecool.faniUMLa.Queststore.DAO.DAOUser;
import com.codecool.faniUMLa.Queststore.DAO.DAOUserInterface;
import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

public class AppController extends Controller {
    DAOUserInterface daoUser;
    DAOAdminInterface daoAdmin;

    public AppController() {
        daoUser = new DAOUser(connection);
        daoAdmin = new DAOAdmin(connection);
        signIn();
    }

    public void handleMenu(UserPrivilege privilege) {
        switch (privilege) {
            case CREATE_MENTOR:
                break;
            case CREATE_CLASS:
                String class_name = askUser("Provide class_name");
                daoAdmin.createClass(class_name);
                break;
            case EDIT_MENTOR:
                view.displayList(daoAdmin.getAllMentors(), "");
                String choosenMentor = askUser("Which mentor would you like to choose(number) ");
                //daoAdmin.editMentor(choosenMentor);
                break;
            case SEE_MENTOR:
                view.displayList(daoAdmin.getAllMentors(), "");
                int mentorIndex = (Integer.valueOf(askUser("Provide mentor's number"))-1);
                view.printLine(daoAdmin.getMentor(mentorIndex).getMentorDetails());
                break;
            case CREATE_LEVELS:
                break;
        }
    }

    public void run() {
        UserPrivilege privilege;
        privilege = choosePrivilege();
        handleMenu(privilege);
    }
}
