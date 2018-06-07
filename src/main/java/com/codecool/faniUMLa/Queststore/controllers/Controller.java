package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOUser;
import com.codecool.faniUMLa.Queststore.DAO.DAOUserInterface;
import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.users.User;
import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Controller extends DBConnectionController {
    public Connection connection = connect();
    View view = new View();
    Scanner scanner = new Scanner(System.in);
    private static User user;
    DAOUser daoUser = new DAOUser(connection);


    public void signIn() {
        String login = askLogin();
        String password = askPassword();
        setUserToNull();

        this.user = daoUser.getUser(login, password);
        if (getUser() == null) {
            view.printLine("Wrong login/password. Try again..");
            signIn();
        }
    }

    private String askLogin() {
        return askUser("Login");
    }

    private String askPassword() {
        return askUser("Password");
    }

    private void setUserToNull() {
        this.user = null;
    }

    public String askUser(String message) {
        view.printLine(message);
        return scanner.nextLine();
    }

    public User getUser() {
        return user;
    }

    public UserPrivilege choosePrivilege() {
        List<UserPrivilege> privileges = user.getAccess().getPrivileges();
        view.displayMenu(privileges);
        Integer answer = Integer.valueOf(askUser("Which option would you like to choose(number)"));
        for(int i = 0; i < privileges.size(); i++) {
            if(answer.equals(i)) {
                return privileges.get(i);
            }
        }
        view.printLine("There's no such option!");
        return choosePrivilege();
    }

}
