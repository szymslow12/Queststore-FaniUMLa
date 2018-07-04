package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOAdmin;
import com.codecool.faniUMLa.Queststore.DAO.DAOAdminInterface;
import com.codecool.faniUMLa.Queststore.DAO.DAOUser;
import com.codecool.faniUMLa.Queststore.DAO.DAOUserInterface;
import com.codecool.faniUMLa.Queststore.model.users.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class DAOUserController extends UriController implements HttpHandler {

    DAOUserInterface daoUser = new DAOUser(connection);
    DAOAdminInterface daoAdmin = new DAOAdmin(connection);

    @Override
    public void handle(HttpExchange httpExchange) {

        String response = "";

        String method = httpExchange.getRequestMethod();
        String subSiteName = getSubSite(httpExchange.getRequestURI().getQuery());

        if (method.equals("GET")) {
            JSONObject obj;
            switch (subSiteName) {
                case "Profile":
                    User user = daoUser.getUser("student", "student");
                    obj = new JSONObject();
                    obj.put("First Name", user.getFirstName());
                    obj.put("Last Name", user.getLastName());
                    obj.put("Phone", user.getPhoneNumber());
                    obj.put("Email", user.getEmail());
                    response = obj.toString();
                    break;
                case "Mentor":

                    break;
                case "Admin":
                    User mentor = daoAdmin.getMentor(getParameter(httpExchange.getRequestURI().getQuery()));
                    obj = new JSONObject();
                    obj.put("First Name", mentor.getFirstName());
                    obj.put("Last Name", mentor.getLastName());
                    obj.put("Phone", mentor.getPhoneNumber());
                    obj.put("Email", mentor.getEmail());
                    response = obj.toString();
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
