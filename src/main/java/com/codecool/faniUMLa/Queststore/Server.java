package com.codecool.faniUMLa.Queststore;
import com.codecool.faniUMLa.Queststore.controllers.*;
import com.codecool.faniUMLa.Queststore.server.*;

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
        server.createContext("/Store", new AppController());
        server.createContext("/Mentors", new AppController());
        server.createContext("/editMentors", new AppController());
        server.createContext("/editStudents", new AppController());
        server.createContext("/editQuests" , new AppController());
        server.createContext("/editArtifacts" , new AppController());
        server.createContext("/editClasses" , new AppController());
        server.createContext("/editLevel" , new AppController());
        server.createContext("/Inventory", new AppController());
        server.createContext("/QuestsMentor", new AppController());
        server.createContext("/Artifacts", new AppController());
        server.createContext("/Quests", new AppController());
        server.createContext("/Discard", new AppController());
        server.createContext("/js/main.js", new Static());
        server.createContext("/js/login-button.js", new Static());
        server.createContext("/daoAdminController", new DAOAdminController());
        server.createContext("/daoMentorController", new DAOMentorController());
        server.createContext("/daoStudentController", new DAOStudentController());
        server.createContext("/DAOUserController", new DAOUserController());
        server.createContext("/js/store.js", new Static());
        server.createContext("/Login", new LoginController());
        server.createContext("/css/main.css", new Static());
        server.createContext("/logOut", new LoginController());
        server.setExecutor(null);
        server.start();
        System.out.println("Connected");
    }
}
