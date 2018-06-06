package com.codecool.faniUMLa.Queststore.model;

import com.codecool.faniUMLa.Queststore.View;

import java.util.Scanner;

public class UserInputs {

    private Scanner scanner;

    public UserInputs() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String message) {
        new View().printLine(message);
        return scanner.nextLine();
    }
}
