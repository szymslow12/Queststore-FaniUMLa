package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOUser;
import com.codecool.faniUMLa.Queststore.DAO.DAOUserInterface;
import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

public class AppController extends Controller {
    DAOUserInterface daoUser;

    public AppController() {
        daoUser = new DAOUser(connection);
        signIn();
    }

    public void handleMenu(UserPrivilege privilege) {
        switch (privilege) {
            case CREATE_MENTOR:
                break;
            case CREATE_CLASS:
                break;
            case EDIT_MENTOR:
                break;
            case SEE_MENTOR:
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
