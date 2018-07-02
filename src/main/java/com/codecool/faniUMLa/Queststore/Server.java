package com.codecool.faniUMLa.Queststore;
import com.codecool.faniUMLa.Queststore.server.*;

import com.codecool.faniUMLa.Queststore.controllers.AppController;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
        server.createContext("/admin", new AppController());
        server.createContext("/student", new AppController());
        server.createContext("/mentor", new AppController());
        server.createContext("/js/main.js", new Static());
        server.createContext("/js/login-button.js", new Static());
        server.createContext("/js/store.js", new Static());
        server.createContext("/css/main.css", new Static());
        server.setExecutor(null);
        server.start();
        System.out.println("Connected");
    }
}
