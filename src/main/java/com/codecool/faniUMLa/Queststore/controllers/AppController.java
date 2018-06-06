package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.model.users.UserPriviledge;

public class AppController {
    ControllerCodecooler controllerCodecooler;
    ControllerMentor controllerMentor;
    ControllerAdmin controllerAdmin;

    public AppController() {
        controllerCodecooler = new ControllerCodecooler();
        controllerMentor = new ControllerMentor();
        controllerAdmin = new Controller (csvDAOEmployee, csvDAOStudent);
        this.setCsvDAOEmployee(csvDAOEmployee);
        this.setCsvDAOStudent(csvDAOStudent);
        signIn();
    }

    public void handleMenu(UserPriviledge privilege) {
        switch (privilege) {
            case ADD_MENTOR:
                employeeController.addMentor();
                break;
            case REMOVE_MENTOR:
                employeeController.removeMentor();
                break;
            default:
                super.errorMessage();
        }
    }

    public void run() {
        UserPriviledge privilege;
        do {
            displayMenu();
            privilege = choosePrivilege();
            handleMenu(privilege);
        } while (isRun(privilege));
    }
}
