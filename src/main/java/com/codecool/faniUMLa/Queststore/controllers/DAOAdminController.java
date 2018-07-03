package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOAdmin;
import com.codecool.faniUMLa.Queststore.DAO.DAOAdminInterface;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.Connection;

public class DAOAdminController implements HttpHandler {
    DAOAdminInterface daoAdmin;

    public DAOAdminController(Connection connection) {
        this.daoAdmin = new DAOAdmin(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
