package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOCodecooler;
import com.codecool.faniUMLa.Queststore.DAO.DAOCodecoolerInterface;
import com.codecool.faniUMLa.Queststore.model.Quest;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.Inventory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DAOStudentController extends UriController implements HttpHandler {
    DAOCodecoolerInterface daoCodecooler =  new DAOCodecooler(connection);

    @Override
    public void handle(HttpExchange httpExchange) {
        String response = "";
        String method = httpExchange.getRequestMethod();
        String subSiteName = getSubSite(httpExchange.getRequestURI().getQuery());

        if (method.equals("GET")) {
            JSONArray json;
            JSONObject jsonObject;
            switch (subSiteName) {
                case "Store":
                    List<Artifact> artifactList = daoCodecooler.showArtifacts();
                    json = new JSONArray();
                    for (Artifact artifact: artifactList) {
                        jsonObject = new JSONObject();
                        jsonObject.put("Name", artifact.getName());
                        jsonObject.put("Description", artifact.getDescription());
                        jsonObject.put("Price", artifact.getPrice());
                        json.put(jsonObject);
                    }
                    response = json.toString();
                    break;
                case "Inventory":
                    // for test and presentation is passed codecoolerID = 1
                    Inventory inv = daoCodecooler.getBoughtArtifacts(1);
                    List<Artifact> boughtArtifacts = inv.getArtifacts();
                    json = new JSONArray();
                    for (Artifact artifact: boughtArtifacts) {
                        int quantity = inv.getArtifactQuantity(artifact.getArtifactID());
                        jsonObject = new JSONObject();
                        jsonObject.put("Name", artifact.getName());
                        jsonObject.put("Description", artifact.getDescription());
                        jsonObject.put("Price", artifact.getPrice());
                        jsonObject.put("Amount", quantity);
                        json.put(jsonObject);
                    }
                    response = json.toString();
                    break;
                case "Quests":
                    // for test and presentation is passed codecoolerID = 1
                    List<Quest> quests = daoCodecooler.getDoneQuests(1);
                    json = new JSONArray();
                    for (Quest quest: quests) {
                        jsonObject = new JSONObject();
                        jsonObject.put("Name", quest.getName());
                        jsonObject.put("Description", quest.getDescription());
                        jsonObject.put("Award", quest.getAward());
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
