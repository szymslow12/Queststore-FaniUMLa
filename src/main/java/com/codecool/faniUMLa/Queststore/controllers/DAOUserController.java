package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.*;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.codecool.faniUMLa.Queststore.model.users.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class DAOUserController extends UriController implements HttpHandler {

    DAOUserInterface daoUser = new DAOUser(connection);
    DAOAdminInterface daoAdmin = new DAOAdmin(connection);
    DAOMentorInterface daoMentor = new DAOMentor(connection);

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
                    User student = daoMentor.getCodecooler(getParameter(httpExchange.getRequestURI().getQuery()));
                    obj = new JSONObject();
                    obj.put("First Name", student.getFirstName());
                    obj.put("Last Name", student.getLastName());
                    obj.put("Phone", student.getPhoneNumber());
                    obj.put("Email", student.getEmail());
                    obj.put("Class", ((Codecooler) student).getClassID());
                    obj.put("Wallet", ((Codecooler) student).getCoolcoins());
                    response = obj.toString();
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
                case "Quests":
                    Quest quest = daoMentor.getQuest(getParameter(httpExchange.getRequestURI().getQuery()));
                    obj = new JSONObject();
                    obj.put("Name", quest.getName());
                    obj.put("Category", quest.getCategory());
                    obj.put("Description", quest.getDescription());
                    obj.put("Award", quest.getAward());
                    response = obj.toString();
                    break;
                case "Artifacts":
                    Artifact artifact = daoMentor.getArtifact(getParameter(httpExchange.getRequestURI().getQuery()));
                    obj = new JSONObject();
                    obj.put("Name", artifact.getName());
                    obj.put("Price", artifact.getPrice());
                    obj.put("Description", artifact.getDescription());
                    obj.put("Category", artifact.getCategory());
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
        } if (method.equals("POST")) {
            switch (subSiteName) {
                case "deleteArtifacts":
                    daoMentor.deleteArtifact(getParameter(httpExchange.getRequestURI().getQuery()));
                    break;
                case "deleteStudent":
                    daoMentor.deleteStudent(getParameter(httpExchange.getRequestURI().getQuery()));
                    break;
                case "deleteQuest":
                    daoMentor.deleteQuest(getParameter(httpExchange.getRequestURI().getQuery()));
                    break;
            }
        }
    }
}
