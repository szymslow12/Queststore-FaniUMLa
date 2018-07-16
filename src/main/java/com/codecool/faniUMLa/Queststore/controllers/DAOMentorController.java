package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOMentor;
import com.codecool.faniUMLa.Queststore.DAO.DAOMentorInterface;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.codecool.faniUMLa.Queststore.utils.StringUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.codehaus.groovy.tools.shell.IO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                case "QuestsMentor":
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

    public String createStudent(HttpExchange httpExchange) throws IOException {

        Map <String, String> inputs = getInputs(httpExchange);

        String first_name = inputs.get("Name");
        String last_name = inputs.get("Surname");
        String email = inputs.get("Email");
        String phone_number = inputs.get("Phone");
        String user_login = StringUtils.generateString();
        String user_password = StringUtils.generateString();

        ArrayList<String> studentData = new ArrayList<>();
        studentData.add(user_login);
        studentData.add(user_password);
        studentData.add(first_name);
        studentData.add(last_name);
        studentData.add(email);
        studentData.add(phone_number);


        daoMentor.createStudent(studentData);
        return getFile("html/mentor/Students.html");
    }

    public String createQuest(HttpExchange httpExchange) throws IOException {

        Map<String, String> inputs = getInputs(httpExchange);
        String name = inputs.get("Name");
        String description = inputs.get("Description");
        int award = Integer.parseInt(inputs.get("Award"));

        daoMentor.createQuest(name, description, award, 1);
        return getFile("html/mentor/QuestsMentor.html");
    }

    public String createArtifact(HttpExchange httpExchange) throws IOException{

        Map<String, String> inputs = getInputs(httpExchange);
        String name = inputs.get("Name");
        String description = inputs.get("Description");
//        int category = Integer.parseInt(inputs.get("Category"));
        int price = Integer.parseInt(inputs.get("Price"));
        daoMentor.createArtifact(name, 1, price, description);
        return getFile("html/mentor/Artifacts.html");
    }

    public String editStudent(HttpExchange httpExchange) throws IOException{

        Map<String, String> inputs = getInputs(httpExchange);

        int id = Integer.parseInt(inputs.get("ID"));
        for(String key : inputs.keySet()) {
            if (!key.equals("ID"))
                daoMentor.editStudent(transform(key), inputs.get(key), id);
        }

        return getFile("html/mentor/Students.html");
    }

    private String transform(String key) {
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("Phone", "phone_number");
        dictionary.put("First+Name", "first_name");
        dictionary.put("Last+Name", "last_name");
        dictionary.put("Email", "email");
        return dictionary.get(key);
    }

    public String editQuest(HttpExchange httpExchange) throws IOException {

        Map<String, String> inputs = getInputs(httpExchange);
        daoMentor.editQuest(inputs);

        return getFile("html/mentor/QuestsMentor.html");
    }

    public String editArtifact(HttpExchange httpExchange) throws IOException {

        Map<String, String> inputs = getInputs(httpExchange);
        daoMentor.editArtifact(inputs);

        return getFile("html/mentor/Artifacts.html");
    }
}
