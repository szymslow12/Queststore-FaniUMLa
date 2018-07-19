package com.codecool.faniUMLa.Queststore.controllers;


import com.codecool.faniUMLa.Queststore.DAO.DAOCodecooler;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import com.codecool.faniUMLa.Queststore.model.store.Inventory;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.mockito.Mockito.when;

class DAOStudentControllerTest {

    @Mock
    private HttpExchange mockHttpExchange;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStmnt;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private DAOCodecooler mockDaoCodecooler;

    private DAOStudentController daoStudentController;
    private OutputStream outputStream;


    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        daoStudentController = new DAOStudentController(mockConnection, mockDaoCodecooler);
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    void testGenerateResponseOfStudentCoolcoins() {
        int coolcoins = 50;
        String response = getStudentCoolcoinsResponse(coolcoins);

        URI uri = URI.create("/daoStudentController?Method=Coolcoins");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.getCoolcoins(any(Integer.class))).thenReturn(coolcoins);
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);
        daoStudentController.handle(mockHttpExchange);

        assertEquals(response, outputStream.toString());
    }

    @Test
    void testGenerateResponseOfArtifactsByCategory() {
        List<Artifact> artifactList = getArtifacts();
        String response = getResponseOfArtifacts(artifactList);

        URI uri = URI.create("/daoStudentController?Method=Store2");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.showArtifacts(any(Integer.class))).thenReturn(artifactList);
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);
        daoStudentController.handle(mockHttpExchange);

        assertEquals(response, outputStream.toString());
    }

    @Test
    void testGetArtifactsByCategoryIfArtifactListIsEmpty() {
        List<Artifact> artifactList = new ArrayList<>();

        URI uri = URI.create("/daoStudentController?Method=Store2");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.showArtifacts(any(Integer.class))).thenReturn(artifactList);
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);
        daoStudentController.handle(mockHttpExchange);

        assertNotNull(outputStream.toString());
    }

    @Test
    void testGenerateResponseOfInventory() {
        List<Artifact> actualArtifactList = getArtifacts();
        Map<Integer, Integer> artifactQuantityMap = getArtifactQuantityMap();
        Inventory inventory = new Inventory(actualArtifactList, artifactQuantityMap);

        URI uri = URI.create("/daoStudentController?Method=Inventory");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.getBoughtArtifacts(any(Integer.class))).thenReturn(inventory);
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);
        daoStudentController.handle(mockHttpExchange);

        String[] expectedStreamArray = getExpectedStreamArray(outputStream);
        Map<String, String> artifact1Map = getMapOfArtifactAtributs(expectedStreamArray[0]);
        Map<String, String> artifact2Map = getMapOfArtifactAtributs(expectedStreamArray[1]);

        assertArtifact(artifact1Map, actualArtifactList.get(0));
        assertArtifact(artifact2Map, actualArtifactList.get(1));

    }

    private void assertArtifact(Map<String, String> artifactFieldMap, Artifact actualArtifact) {
        assertEquals(actualArtifact.getName(), artifactFieldMap.get("Name"));
        assertEquals(actualArtifact.getPrice(), Integer.parseInt(artifactFieldMap.get("Price")));
        assertEquals(actualArtifact.getDescription(), artifactFieldMap.get("Description"));
    }

    private String[] getExpectedStreamArray(OutputStream outputStream) {
        String expectedStream = outputStream.toString();
        expectedStream = expectedStream.replaceAll("\\[\\{", "");
        expectedStream = expectedStream.replaceAll("}]", "");
        expectedStream = expectedStream.replaceAll("\"", "");
        return expectedStream.split("},\\{");
    }

    private Map<Integer, Integer> getArtifactQuantityMap() {
        Map<Integer, Integer> artifactQuantityMap = new HashMap<>();
        artifactQuantityMap.put(1, 2);
        artifactQuantityMap.put(2, 3);
        return artifactQuantityMap;
    }

    private Map getMapOfArtifactAtributs(String text) {
        Map<String, String> atributMap = new HashMap<>();
        String[] atributArray = text.split(",");
        for(String atribut : atributArray) {
            String[] fieldArray = atribut.split(":");
            atributMap.put(fieldArray[0], fieldArray[1]);
        }

        return atributMap;

    }

    private String getStudentCoolcoinsResponse(int coolcoins) {
        JSONArray json = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Coolcoins", coolcoins);
        json.put(jsonObject);
        return json.toString();
    }

    private String getResponseOfArtifacts(List<Artifact> artifactList) {
        JSONArray json = new JSONArray();
        for (Artifact artifact: artifactList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("artifact_id", artifact.getArtifactID());
            jsonObject.put("Name", artifact.getName());
            jsonObject.put("Description", artifact.getDescription());
            jsonObject.put("Price", artifact.getPrice());
            json.put(jsonObject);
        }

        return json.toString();
    }

    private List<Artifact> getArtifacts() {
        List<Artifact> artifactList = new ArrayList<>();
        artifactList.add(new Artifact(1, "Artifact 1", 1, 20, "Description"));
        artifactList.add(new Artifact(2, "Artifact 2", 1, 30, "Description"));
        return artifactList;
    }
}