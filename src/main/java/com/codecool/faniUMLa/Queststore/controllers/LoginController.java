package com.codecool.faniUMLa.Queststore.controllers;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class LoginController extends UriController implements HttpHandler {
    DAOLoginController daoLoginController = new DAOLoginController();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            response= this.getFile("html/login.html");

        } else if (method.equals("POST")) {

            response = daoLoginController.log(httpExchange);
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
