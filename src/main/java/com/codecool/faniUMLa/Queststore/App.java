package com.codecool.faniUMLa.Queststore;

import com.codecool.faniUMLa.Queststore.controllers.AppController;
import com.codecool.faniUMLa.Queststore.controllers.ServerConnection;

public class App {
    public static void main(String[] args) {

        System.out.println("Queststore");
        ServerConnection serverConnection = new ServerConnection(2020);
        AppController controller = new AppController();
        //controller.run();
    }
}