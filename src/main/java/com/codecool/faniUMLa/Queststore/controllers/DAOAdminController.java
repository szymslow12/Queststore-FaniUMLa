package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOAdmin;
import com.codecool.faniUMLa.Queststore.DAO.DAOAdminInterface;
import com.codecool.faniUMLa.Queststore.model.store.Level;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;
import com.codecool.faniUMLa.Queststore.utils.StringUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DAOAdminController extends UriController implements HttpHandler {
    DAOAdminInterface daoAdmin = new DAOAdmin(connection);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String subSiteName = getSubSite(httpExchange.getRequestURI().getQuery());

        if (method.equals("GET")) {
            JSONArray json;
            JSONObject obj;
            switch(subSiteName) {
                case "Mentors":
                    ArrayList<Mentor> mentorsList = daoAdmin.getAllMentors();
                    json = new JSONArray();
                    for (Mentor mentor : mentorsList) {
                        obj = new JSONObject();
                        obj.put("ID", mentor.getIdUser());
                        obj.put("First Name", mentor.getFirstName());
                        obj.put("Last Name", mentor.getLastName());
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
                case "Classes":
                    Map<Integer, String> classList = daoAdmin.getAllClasses();
                    json = new JSONArray();
                    for (Integer key : classList.keySet()) {
                        obj = new JSONObject();
                        obj.put("ID", key);
                        obj.put("Class Name", classList.get(key));
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
                case "Levels":
                    ArrayList<Level> levelList = daoAdmin.getAllLevels();
                    json = new JSONArray();
                    for (Level level : levelList) {
                        obj = new JSONObject();
                        obj.put("ID", level.getId_level());
                        obj.put("Level Name", level.getLevel_name());
                        obj.put("Level Threshold", level.getThreshold_level());
                        json.put(obj);
                    }
                    response = json.toString();
                    break;
            }
        }

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        public String createLevel(HttpExchange httpExchange) throws IOException {

            Map inputs = getInputs(httpExchange);

            String levelName = String.valueOf(inputs.get("Level+Name"));
            Integer thresholdLevel = Integer.valueOf((String)inputs.get("Level+Threshold"));

            daoAdmin.createLevel(levelName, thresholdLevel);
            return this.getFile("html/admin/Levels.html");
        }

    public String createClass(HttpExchange httpExchange) throws IOException {

        Map inputs = getInputs(httpExchange);

        String className = String.valueOf(inputs.get("Class+Name"));

        daoAdmin.createClass(className);
        return this.getFile("html/admin/Classes.html");
    }

    public String createMentor(HttpExchange httpExchange) throws IOException {

        Map inputs = getInputs(httpExchange);

        String first_name = String.valueOf(inputs.get("Name"));
        String last_name = String.valueOf(inputs.get("Surname"));
        String email = String.valueOf(inputs.get("Email"));
        String phone_number = String.valueOf(inputs.get("Phone "));
        String user_login = StringUtils.generateString();
        String user_password = StringUtils.generateString();
        String classes = String.valueOf(inputs.get("Classes"));

        ArrayList<String> mentorsData = new ArrayList<>();
        mentorsData.add(first_name);
        mentorsData.add(last_name);
        mentorsData.add(email);
        mentorsData.add(phone_number);
        mentorsData.add(user_login);
        mentorsData.add(user_password);
        mentorsData.add(classes);

        daoAdmin.createMentor(mentorsData);
        return this.getFile("html/admin/Mentors.html");
    }

    public String editMentor(HttpExchange httpExchange) throws IOException {

        Map<String, String> inputs = getInputs(httpExchange);
        int id = Integer.parseInt(inputs.get("ID"));
        for(String key : inputs.keySet()) {
            if (!key.equals("ID"))
            daoAdmin.editMentor(transform(key), inputs.get(key), id);
        }

        return getFile("html/admin/Mentors.html");
    }

    public String editClass(HttpExchange httpExchange) throws IOException {

        Map<String, String> inputs = getInputs(httpExchange);
        daoAdmin.editClass(Integer.parseInt(inputs.get("ID")), inputs.get("Name"));

        return getFile("html/admin/Classes.html");
    }

    public String editLevel(HttpExchange httpExchange) throws IOException {

        Map<String, String> inputs = getInputs(httpExchange);
        daoAdmin.editLevel(Integer.parseInt(inputs.get("ID")), inputs.get("Name"), Integer.parseInt(inputs.get("Level+Threshold")));

        return getFile("html/admin/Levels.html");
    }

    private String transform(String key) {
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("Phone", "phone_number");
        dictionary.put("First+Name", "first_name");
        dictionary.put("Last+Name", "last_name");
        dictionary.put("Email", "email");
        return dictionary.get(key);
    }


}
