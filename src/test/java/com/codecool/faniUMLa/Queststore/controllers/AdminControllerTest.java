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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private List<Mentor> exemplaryMentors() {
        List<Mentor> mentors = new ArrayList<>();
        Mentor mentorEla = new Mentor(1, "Ela", "Krzych", "ela@codecool.com", "123");
        Mentor mentorLukas = new Mentor(2, "Lukas", "Wrona", "lukas@codecool.com", "123");
        mentors.add(mentorEla);
        mentors.add(mentorLukas);
        return mentors;
    }

    @Test
    void testHandle_GetMentorsWhenIsNull() {
        assertThrows(IllegalArgumentException.class, () -> testGetMentors(null));
    }

    @Test
    void testHandle_GetExemplaryMentors() throws Exception {
        List<Mentor> mentors = exemplaryMentors();
        OutputStream actualOutputStream = testGetMentors(mentors);

        assertListMentorsWithOutputStream(mentors, actualOutputStream);
    }

    private OutputStream testGetMentors(List<Mentor> mentors) throws Exception {
        DAOAdminController adminController = new DAOAdminController(mockConnection, mockDaoAdmin);
        URI uriAdminControllerForMentors = URI.create("/daoAdminController?Method=Mentors");
        OutputStream outputStream = new ByteArrayOutputStream();

        when(mockHttpExchange.getRequestMethod()).thenReturn("GET");
        when(mockHttpExchange.getRequestURI()).thenReturn(uriAdminControllerForMentors);
        when(mockDaoAdmin.getAllMentors()).thenReturn(mentors);
        when(mockHttpExchange.getResponseBody()).thenReturn(outputStream);

        adminController.handle(mockHttpExchange);

        return outputStream;
    }

    private void assertListMentorsWithOutputStream(List<Mentor> mentors, OutputStream outputStream) {
        String[] mentorsAsString = outputStream.toString().split("},\\{");
        String[] mentorAsString;
        int nameIndex = 2;
        int lastNameIndex = 6;
        int idIndex = 4;

        assertEquals(mentors.size(), mentorsAsString.length);

        for (int i = 0; i < mentorsAsString.length; i++) {
            mentorAsString = mentorsAsString[i].split("[\\[:,{\"}\\]]{1,}");

            assertEquals(mentors.get(i).getFirstName(), mentorAsString[nameIndex]);
            assertEquals(mentors.get(i).getLastName(), mentorAsString[lastNameIndex]);
            assertEquals(mentors.get(i).getIdUser(), Integer.parseInt(mentorAsString[idIndex]));
        }
    }
}