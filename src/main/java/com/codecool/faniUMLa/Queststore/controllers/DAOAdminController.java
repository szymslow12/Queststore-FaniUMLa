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
            JSONArray json;
            JSONObject obj;
            switch(subSiteName) {
                case "Admin":
                    ArrayList<Mentor> mentorsList = daoAdmin.getAllMentors();
                    json = new JSONArray();
                    for (Mentor mentor : mentorsList) {
                        obj = new JSONObject();
                        obj.put("First Name", mentor.getFirstName());
                        obj.put("Last Name", mentor.getLastName());
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
                case "Classes":
                    ArrayList<String> classList = daoAdmin.getAllClasses();
                    json = new JSONArray();
                    for (String c : classList) {
                        obj = new JSONObject();
                        obj.put("Class Name", c);
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
                case "Levels":
                    ArrayList<String> levelList = daoAdmin.getAllLevels();
                    json = new JSONArray();
                    for (String level : levelList) {
                        obj = new JSONObject();
                        obj.put("Class Name", level);
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
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
