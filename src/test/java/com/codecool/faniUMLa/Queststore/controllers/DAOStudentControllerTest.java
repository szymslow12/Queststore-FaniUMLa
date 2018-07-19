package com.codecool.faniUMLa.Queststore.controllers;


import com.codecool.faniUMLa.Queststore.DAO.DAOCodecooler;
import com.codecool.faniUMLa.Queststore.model.store.Artifact;
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
import java.util.List;


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


    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetStudentCoolcoins() {
        int coolcoins = 50;

        JSONArray json = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Coolcoins", coolcoins);
        json.put(jsonObject);
        String response = json.toString();

        daoStudentController = new DAOStudentController(mockConnection, mockDaoCodecooler);
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        URI uri = URI.create("/daoStudentController?Method=Coolcoins");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.getCoolcoins(any(Integer.class))).thenReturn(coolcoins);

        OutputStream outputStream = new ByteArrayOutputStream();
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);

        daoStudentController.handle(mockHttpExchange);

        assertEquals(response, outputStream.toString());
    }

    @Test
    void testGetArtifactsByCategory() {
        int categoryId = 1;
        List<Artifact> artifactList = new ArrayList<>();
        artifactList.add(new Artifact(1, "Artifact 1", 1, 20, "Description"));
        artifactList.add(new Artifact(2, "Artifact 2", 1, 30, "Description"));

        JSONArray json = new JSONArray();
        for (Artifact artifact: artifactList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("artifact_id", artifact.getArtifactID());
            jsonObject.put("Name", artifact.getName());
            jsonObject.put("Description", artifact.getDescription());
            jsonObject.put("Price", artifact.getPrice());
            json.put(jsonObject);
        }

        String response = json.toString();

        daoStudentController = new DAOStudentController(mockConnection, mockDaoCodecooler);
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        URI uri = URI.create("/daoStudentController?Method=Store2");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.showArtifacts(any(Integer.class))).thenReturn(artifactList);

        OutputStream outputStream = new ByteArrayOutputStream();
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);

        daoStudentController.handle(mockHttpExchange);

        assertEquals(response, outputStream.toString());
    }

    @Test
    void testGetArtifactsByCategoryIfArtifactListIsEmpty() {
        int categoryId = 1;

        List<Artifact> artifactList = new ArrayList<>();

        daoStudentController = new DAOStudentController(mockConnection, mockDaoCodecooler);
        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        URI uri = URI.create("/daoStudentController?Method=Store2");
        when(mockHttpExchange.getRequestURI()).thenReturn(uri);
        when(mockDaoCodecooler.showArtifacts(any(Integer.class))).thenReturn(artifactList);

        OutputStream outputStream = new ByteArrayOutputStream();
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);

        daoStudentController.handle(mockHttpExchange);

        assertNotNull(outputStream.toString());
    }
}