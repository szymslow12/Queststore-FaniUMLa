package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppController extends Controller implements HttpHandler {
    DAOMentorInterface daoMentor;
    DAOCodecoolerInterface daoCodecooler;
    //UserInputs inputs;

    public AppController() {
        daoMentor = new DAOMentor(connection);
        daoCodecooler = new DAOCodecooler(connection);

    }

    @Override
    public void handle (HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            System.out.println("in get");
            switch(httpExchange.getRequestURI().toString()) {
                case "/Mentors":
                    response= this.getFile("html/admin/Mentors.html");
                    break;
                case "/Classes":
                    response= this.getFile("html/admin/Classes.html");
                    break;
                case "/Levels":
                    response= this.getFile("html/admin/Levels.html");
                    break;
                case"/Students":
                    response= this.getFile("html/mentor/Students.html");
                    break;
                case "/Artifacts":
                    response= this.getFile("html/mentor/Artifacts.html");
                    break;
                case "/QuestsMentor":
                    response= this.getFile("html/mentor/QuestsMentor.html");
                    break;
                case "/Store":
                    response= this.getFile("html/student/Store.html");
                    break;
                case "/Inventory":
                    response= this.getFile("html/student/Inventory.html");
                    break;
                case "/Quests":
                    response= this.getFile("html/student/Quests.html");
                    break;
                case "/Discard":
                    response= this.getFile("html/student/Discard.html");
                    break;
            }
        } else if (method.equals("POST")) {
            DAOAdminController daoAdminController = new DAOAdminController();
            DAOMentorController daoMentorController = new DAOMentorController();
            switch(httpExchange.getRequestURI().toString()) {
                case "/Levels":
                    response = daoAdminController.createLevel(httpExchange);
                    break;
                case "/Classes":
                    response = daoAdminController.createClass(httpExchange);
                    break;
                case "/Mentors":
                    response = daoAdminController.createMentor(httpExchange);
                    break;
                case "/Students":
                    response = daoMentorController.createStudent(httpExchange);
                    break;
                case "/QuestsMentor":
                    response = daoMentorController.createQuest(httpExchange);
                    break;
                case "/Artifacts":
                    response = daoMentorController.createArtifact(httpExchange);
                    break;
                case "/editMentors":
                    response = daoAdminController.editMentor(httpExchange);
                    redirect(httpExchange, "/Mentors");
                    break;
                case "/editClasses":
                    response = daoAdminController.editClass(httpExchange);
                    redirect(httpExchange, "/Classes");
                    break;
                case "/editLevels":
                    response = daoAdminController.editLevel(httpExchange);
                    redirect(httpExchange, "/Levels");
                    break;
                case "/editStudents":
                    response = daoMentorController.editStudent(httpExchange);
                    redirect(httpExchange, "/Students");
                    break;
                case "/editQuestsMentor":
                    response = daoMentorController.editQuest(httpExchange);
                    redirect(httpExchange, "/QuestsMentor");
                    break;
                case "/editArtifacts":
                    response = daoMentorController.editArtifact(httpExchange);
                    redirect(httpExchange, "/Artifacts");
                    break;
            }
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String getFile(String filepath) {
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

    public void redirect(HttpExchange httpExchange, String location) throws IOException {
        Headers headers = httpExchange.getResponseHeaders();
        headers.add("Location", location);
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }

    public static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
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

    public Map<String, String> getInputs(HttpExchange httpExchange) throws IOException{

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        return parseFormData(formData);
    }
}
