package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOAdmin;
import com.codecool.faniUMLa.Queststore.DAO.DAOAdminInterface;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class DAOAdminController extends UriController implements HttpHandler {
    DAOAdminInterface daoAdmin = new DAOAdmin(connection);

    @Override
    public void handle(HttpExchange httpExchange) {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String subSiteName = getSubSite(httpExchange.getRequestURI().getQuery());

        if(method.equals("GET")) {
            switch(subSiteName) {
                case "Admin":
                    ArrayList<Mentor> mentorsList = daoAdmin.getAllMentors();
                    JSONArray json = new JSONArray();
                    for (Mentor mentor : mentorsList) {
                        JSONObject obj = new JSONObject();
                        obj.put("First Name", mentor.getFirstName());
                        obj.put("Last Name", mentor.getLastName());
                        json.put(obj);
                    }
                    response = json.toString();
            }

            try {
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
