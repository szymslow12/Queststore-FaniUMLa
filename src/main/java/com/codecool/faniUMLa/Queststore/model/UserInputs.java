package com.codecool.faniUMLa.Queststore.model;

import com.codecool.faniUMLa.Queststore.View;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInputs {

    public String getString(String message) {
        new View().printLine(message);
        return new Scanner(System.in).nextLine();
    }


    public int getInt(String message) {
        Scanner scanner = new Scanner(System.in);
        boolean isCorrectInput = false;
        int number = 0;
        while (!isCorrectInput) {
            try {
                new View().printLine(message);
                number = scanner.nextInt();
                isCorrectInput = true;
            } catch (InputMismatchException err) {
                new View().printLine("Wrong input, number needed");
            }
        }
        return number;
    }
}
