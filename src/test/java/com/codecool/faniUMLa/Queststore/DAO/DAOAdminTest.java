package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Classroom;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;
import com.codecool.faniUMLa.Queststore.model.users.UserAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DAOAdminTest {
    private DAOAdmin daoAdmin;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStmnt;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddOneClass() throws SQLException {
        String className = "testClassName";
        daoAdmin = new DAOAdmin(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.executeUpdate(any())).thenReturn(1);

        daoAdmin.createClass(className);

        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString("class_name")).thenReturn(className);
        when(mockResultSet.getInt("id_class")).thenReturn(1);


        List<Classroom> actualClasses = daoAdmin.getAllClasses();
        assertEquals(1, actualClasses.size());
        assertEquals(className, actualClasses.get(0).getClass_name());
        assertEquals(1, actualClasses.get(0).getClass_id());
    }

    @Test
    void testAddThreeClasses() throws SQLException {
        String className = "testClassName";
        daoAdmin = new DAOAdmin(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.executeUpdate(any())).thenReturn(1);

        daoAdmin.createClass(className);
        daoAdmin.createClass(className);
        daoAdmin.createClass(className);

        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
                Boolean.FALSE);
        when(mockResultSet.getString("class_name")).thenReturn(className);
        when(mockResultSet.getInt("id_class")).thenReturn(1).thenReturn(2).thenReturn(3);


        List<Classroom> actualClasses = daoAdmin.getAllClasses();
        assertEquals(3, actualClasses.size());
    }

    @Test
    void testGetAllMentors() throws SQLException {
        int userId = 1;
        String firstName = "first name";
        String lastName = "last name";
        String email = "email";
        String phoneNumber = "phone number";

        daoAdmin = new DAOAdmin(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        when(mockResultSet.getInt("id_user")).thenReturn(userId);
        when(mockResultSet.getString("first_name")).thenReturn(firstName);
        when(mockResultSet.getString("last_name")).thenReturn(lastName);
        when(mockResultSet.getString("email")).thenReturn(email);
        when(mockResultSet.getString("phone_number")).thenReturn(phoneNumber);

        List<Mentor> actualMentors = daoAdmin.getAllMentors();
        assertEquals(1, actualMentors.size());

        Mentor actualFirstMentor = actualMentors.get(0);
        assertEquals(actualFirstMentor.getIdUser(), userId);
        assertEquals(actualFirstMentor.getFirstName(), firstName);
        assertEquals(actualFirstMentor.getLastName(), lastName);
        assertEquals(actualFirstMentor.getEmail(), email);
        assertEquals(actualFirstMentor.getPhoneNumber(), phoneNumber);
        assertEquals(actualFirstMentor.getAccess(), UserAccess.MENTOR);

    }


}