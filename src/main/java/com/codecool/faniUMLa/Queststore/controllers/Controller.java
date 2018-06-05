package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.View;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.sql.Connection;
import java.util.Scanner;

public class Controller implements QueryInterface, DBConnectionInterface{
    Connection conection = connect();
    View view = new View();
    Scanner scanner = new Scanner(System.in);
    private static User user;
    ////////

    public void signIn() {
        String login = askLogin();
        String password = askPassword();
        setUserToNull();

//        searchStudent(login, password);// sprawdzaj po tabelach czy jest
//        if (getUser() == null) {
//            searchEmployee(login, password);
//        }
//        if (getUser() == null) {
//            view.printLine("Wrong login/password. Try again..");
//            signIn();
//        }
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
