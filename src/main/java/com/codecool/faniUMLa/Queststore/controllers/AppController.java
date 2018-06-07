package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOUser;
import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

public class AppController extends Controller {
    DAOUser dauUser;

    public AppController() {
        dauUser = new DAOUser(connection);
        signIn();
    }

    public void handleMenu(UserPrivilege privilege) {
        switch (privilege) {
            case CREATE_MENTOR:
                //employeeController.addMentor();
                // break;
                System.out.println("Im in menu");
                //}
        }
    }

    public void run() {
        UserPrivilege privilege;
        privilege = choosePrivilege();
        handleMenu(privilege);
    }
}
