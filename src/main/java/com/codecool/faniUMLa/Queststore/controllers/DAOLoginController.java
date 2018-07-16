package com.codecool.faniUMLa.Queststore.controllers;


import com.codecool.faniUMLa.Queststore.DAO.DAOLogin;
import com.codecool.faniUMLa.Queststore.model.users.User;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class DAOLoginController extends UriController {
    User user;
    DAOLogin daoLogin = new DAOLogin(connection);

    public String log(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        Map inputs = parseFormData(formData);

        String login = String.valueOf(inputs.get("login"));
        String password = String.valueOf(inputs.get("password"));
        this.user = daoLogin.logIn(login, password);

       if(user.getAccess().name().equals("ADMIN")) {
           return this.getFile("html/admin/Mentors.html");

       } else if (user.getAccess().name().equals("MENTOR")) {
           return this.getFile("html/mentor/Students.html");

       } else if (user.getAccess().name().equals("CODECOOLER")) {
           return this.getFile("html/student/Store.html");
       } else {
           return this.getFile("html/login.html");
       }
    }

}
