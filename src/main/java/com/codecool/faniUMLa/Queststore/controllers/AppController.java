package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

public class AppController extends Controller {
    //ControllerCodecooler controllerCodecooler;
    //ControllerMentor controllerMentor;
    //ControllerAdmin controllerAdmin;

    public AppController() {
        //controllerCodecooler = new ControllerCodecooler();
        //controllerMentor = new ControllerMentor();
        //controllerAdmin = new Controller (csvDAOEmployee, csvDAOStudent);
        signIn();
    }

    public void handleMenu(UserPrivilege privilege) {
        switch (privilege) {
            case GREET:
                //employeeController.addMentor();
                // break;
                System.out.println("Im in menu");
                //}
        }
    }

    public void run() {
        UserPrivilege privilege;

        //displayMenu();
        privilege = choosePrivilege();
        handleMenu(privilege);
    }
}
