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
                    ArrayList<Level> levelList = daoAdmin.getAllLevels();
                    json = new JSONArray();
                    for (Level level : levelList) {
                        obj = new JSONObject();
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
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = parseFormData(formData);

            String levelName = String.valueOf(inputs.get("Level+Name"));
            Integer thresholdLevel = Integer.valueOf((String)inputs.get("Level+Threshold"));

            daoAdmin.createLevel(levelName, thresholdLevel);
            return this.getFile("html/admin/Levels.html");
        }

    public String createClass(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        Map inputs = parseFormData(formData);

        String className = String.valueOf(inputs.get("Class+Name"));

        daoAdmin.createClass(className);
        return this.getFile("html/admin/Classes.html");
    }

    public String createMentor(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        Map inputs = parseFormData(formData);

        String first_name = String.valueOf(inputs.get("Name"));
        String last_name = String.valueOf(inputs.get("Surname"));
        String email = String.valueOf(inputs.get("Email"));
        String phone_number = String.valueOf(inputs.get("Surname"));
        String user_login = StringUtils.generateString();
        String user_password = StringUtils.generateString();

        ArrayList<String> mentorsData = new ArrayList<>();
        mentorsData.add(first_name);
        mentorsData.add(last_name);
        mentorsData.add(email);
        mentorsData.add(phone_number);
        mentorsData.add(user_login);
        mentorsData.add(user_password);

        daoAdmin.createMentor(mentorsData);
        return this.getFile("html/admin/Mentors.html");
    }



    private String getFile(String filepath) {
        StringBuilder result = new StringBuilder("");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filepath).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
