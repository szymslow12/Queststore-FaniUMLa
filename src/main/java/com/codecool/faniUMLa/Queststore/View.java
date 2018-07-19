package com.codecool.faniUMLa.Queststore;

import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;

import java.util.List;

public class View {
    public void printLine(String message) {
        System.out.println(message);
    }

    public void displayMenu(List<UserPrivilege> privileges) {
        printLine("What would you like to do:");
        for (int i = 0; i < privileges.size(); i++) {
            System.out.printf("\t(%d) %s\n", i, privileges.get(i).toString());
        }
    }

    public void clearScreen() {
        printLine("\033[H\033[2J");
    }

    public <E> void displayList(List<E> list, String message) {
        clearScreen();
        printLine(message);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("\t(%d) %s\n", i+1, list.get(i).toString());
        }
    }
}