package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOMentor;
import com.codecool.faniUMLa.Queststore.DAO.DAOMentorInterface;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DAOMentorController extends  UriController implements HttpHandler {

    DAOMentorInterface daoMentor = new DAOMentor(connection);

    @Override
    public void handle(HttpExchange httpExchange) {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String subSiteName = getSubSite(httpExchange.getRequestURI().getQuery());

        if (method.equals("GET")) {
            JSONArray json;
            JSONObject obj;
            switch (subSiteName) {
                case "Students":
                    ArrayList<Codecooler> studentsList = (ArrayList<Codecooler>)daoMentor.getAllStudents();
                    json = new JSONArray();
                    for (Codecooler student : studentsList) {
                        obj = new JSONObject();
                        obj.put("ID", student.getIdUser());
                        obj.put("First Name", student.getFirstName());
                        obj.put("Last Name", student.getLastName());
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
                case "Quests":
                    ArrayList<Quest> quests = (ArrayList)daoMentor.getAllQuests();
                    json = new JSONArray();
                    for (Quest quest : quests) {
                        obj = new JSONObject();
                        obj.put("ID", quest.getQuestID());
                        obj.put("Name", quest.getName());
                        obj.put("Award", quest.getAward());
                        obj.put("Description", quest.getDescription());
                        obj.put("Category", quest.getCategory());
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
                case "Artifacts":
                    ArrayList<Artifact> artifacts = (ArrayList)daoMentor.getAllArtifacts();
                    json = new JSONArray();
                    for (Artifact artifact : artifacts) {
                       obj = new JSONObject();
                       obj.put("ID", artifact.getArtifactID());
                       obj.put("Name", artifact.getName());
                       obj.put("Price", artifact.getPrice());
                       obj.put("Description", artifact.getDescription());
                       obj.put("Category", artifact.getCategory());
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
