package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOUser;
import com.codecool.faniUMLa.Queststore.DAO.DAOUserInterface;
import com.codecool.faniUMLa.Queststore.View;
import com.codecool.faniUMLa.Queststore.model.users.User;

import java.sql.Connection;
import java.util.Scanner;

public class Controller extends DBConnectionController {
    Connection connection = connect();
    View view = new View();
    Scanner scanner = new Scanner(System.in);
    private static User user;
    DAOUserInterface daoUser = new DAOUser();


    public void signIn() {
        String login = askLogin();
        String password = askPassword();
        setUserToNull();

        this.user = daoUser.searchUser(login, password);
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

    private String askUser(String message) {
        view.printLine(message);
        return scanner.nextLine();
    }

    private User getUser() {
        return user;
    }

}
