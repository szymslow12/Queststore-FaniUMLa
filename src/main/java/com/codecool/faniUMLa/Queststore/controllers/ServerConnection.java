package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.model.Static;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerConnection {

    private HttpServer httpServer;

    public ServerConnection(int port) {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            start();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private void handleServerContent() {
        httpServer.createContext("/static", new Static());
        httpServer.createContext("/static/css", new Static());
        httpServer.createContext("/static/js", new Static());
    }


    private void start() {
        handleServerContent();
        httpServer.setExecutor(null);
        httpServer.start();
    }
}
