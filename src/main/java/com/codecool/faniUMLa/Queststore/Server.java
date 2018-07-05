package com.codecool.faniUMLa.Queststore;
import com.codecool.faniUMLa.Queststore.controllers.DAOAdminController;
import com.codecool.faniUMLa.Queststore.controllers.DAOMentorController;
import com.codecool.faniUMLa.Queststore.controllers.DAOUserController;
import com.codecool.faniUMLa.Queststore.server.*;

import com.codecool.faniUMLa.Queststore.controllers.AppController;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001),0);
        server.createContext("/Admin", new AppController());
        server.createContext("/Classes", new AppController());
        server.createContext("/Levels", new AppController());
        server.createContext("/Students", new AppController());
        server.createContext("/Mentors", new AppController());
        server.createContext("/Inventory", new AppController());
        server.createContext("/QuestsMentor", new AppController());
        server.createContext("/Artifacts", new AppController());
        server.createContext("/Quests", new AppController());
        server.createContext("/Store", new AppController());
        server.createContext("/js/main.js", new Static());
        server.createContext("/js/login-button.js", new Static());
        server.createContext("/daoAdminController", new DAOAdminController());
        server.createContext("/daoMentorController", new DAOMentorController());
        server.createContext("/DAOUserController", new DAOUserController());
        server.createContext("/js/store.js", new Static());
        server.createContext("/css/main.css", new Static());
        server.setExecutor(null);
        server.start();
        System.out.println("Connected");
    }
}
