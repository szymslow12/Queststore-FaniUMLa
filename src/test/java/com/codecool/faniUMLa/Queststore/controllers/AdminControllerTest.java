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
    void testGetListMentors() throws Exception {
        List<Mentor> mentors = exampleMentors();
        DAOAdminController adminController = new DAOAdminController(mockConnection, mockDaoAdmin);
        URI uriAdminControllerForMentors = URI.create("/daoAdminController?Method=Mentors");
        OutputStream actualOutputStream = new ByteArrayOutputStream();

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(uriAdminControllerForMentors);
        when(mockDaoAdmin.getAllMentors()).thenReturn(mentors);
        when(mockHttpExchange.getResponseBody()).thenReturn(actualOutputStream);

        adminController.handle(mockHttpExchange);

        assertOutputStreamWithListMentors(actualOutputStream, mentors);
    }

    private void assertOutputStreamWithListMentors(OutputStream outputStream, List<Mentor> mentors) {
        String[] mentorsAsString = outputStream.toString().split("},\\{");
        String[] mentorAsString;

        assertEquals(mentorsAsString.length, mentors.size());

        for (int i = 0; i < mentorsAsString.length; i++) {
            mentorAsString = mentorsAsString[i].split("[\\[:,{\"}\\]]{1,}");

            assertEquals(mentors.get(i).getFirstName(), mentorAsString[2]);
            assertEquals(mentors.get(i).getLastName(), mentorAsString[6]);
            assertEquals(mentors.get(i).getIdUser(), Integer.parseInt(mentorAsString[4]));
        }
    }
}