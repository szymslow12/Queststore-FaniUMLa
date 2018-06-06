package com.codecool.faniUMLa.Queststore;

import com.codecool.faniUMLa.Queststore.model.users.UserPrivilege;
import netscape.security.Privilege;

import java.util.List;

public class View {
    public void printLine(String message) {
        System.out.println(message);
    }

    public void displayMenu(List<UserPrivilege> privileges) {
        clearScreen();
        printLine("What would you like to do:");
        for (int i = 0; i < privileges.size(); i++) {
            System.out.printf("\t(%d) %s\n", i, privileges.get(i).toString());
        }
    }

    public void clearScreen() {
        printLine("\033[H\033[2J");
    }
}
