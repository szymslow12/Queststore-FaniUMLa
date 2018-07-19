package com.codecool.faniUMLa.Queststore.controllers;

import com.codecool.faniUMLa.Queststore.DAO.DAOAdmin;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

class AdminControllerTest {
    @Mock
    private HttpExchange mockHttpExchange;
    @Mock
    private DAOAdmin mockDaoAdmin;
    @Mock
    private Connection mockConnection;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private List<Mentor> exampleMentors() {
        List<Mentor> mentors = new ArrayList<>();
        Mentor mentorEla = new Mentor(1, "Ela", "Krzych", "ela@codecool.com", "123");
        Mentor mentorLukas = new Mentor(2, "Lukas", "Wrona", "lukas@codecool.com", "123");
        mentors.add(mentorEla);
        mentors.add(mentorLukas);
        return mentors;
    }

    @Test
    public void testGetListMentors() throws Exception {
        ArrayList<Mentor> mentors = exampleMentors();
        DAOAdminController adminController = new DAOAdminController(mockConnection, mockDaoAdmin);

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        URI uriAdmin = URI.create("/daoAdminController?Method=Mentors");
        when(mockHttpExchange.getRequestURI()).thenReturn(uriAdmin);

        when(mockDaoAdmin.getAllMentors()).thenReturn(mentors);

        OutputStream outputStream = new ByteArrayOutputStream();
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);

        adminController.handle(mockHttpExchange);

        assertEquals(outputStream.toString(), mentors.toString());
    }
}