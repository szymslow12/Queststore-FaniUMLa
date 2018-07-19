package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DAOMentorTest {

    private static final int INDEX_COLUMN_FIRST_NAME = 5;
    private static final int INDEX_CODECOOLER_FIRST = 0;

    private DAOMentor daoMentor;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStmnt;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllStudentsCheckListSize() throws SQLException {
        returnCommonValuesWhenMethodsInvoked();

        int expectedArraySize = 1;

        List<Codecooler> studentsActual = daoMentor.getAllStudents();
        int actualArraySize = studentsActual.size();

        assertEquals(expectedArraySize, actualArraySize);
    }

    @Test
    void testGetCodecoolerByIndexEqualsFirstName() throws SQLException {
        returnCommonValuesWhenMethodsInvoked();
        when(mockResultSet.getString(INDEX_COLUMN_FIRST_NAME)).thenReturn("John");

        String expectedFirstName = "John";

        Codecooler student = daoMentor.getCodecooler(INDEX_CODECOOLER_FIRST);
        String actualFirstName = student.getFirstName();

        assertEquals(expectedFirstName, actualFirstName);
    }

    @Test
    void testGetCodecoolerThatNotExistsByListIndex() throws SQLException {
        returnCommonValuesWhenMethodsInvoked();

        List<Codecooler> students = daoMentor.getAllStudents();
        int indexNotExistingCodecooler = students.size() + 1;

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            daoMentor.getCodecooler(indexNotExistingCodecooler);
        });
    }

    private void returnCommonValuesWhenMethodsInvoked() throws SQLException {
        daoMentor = new DAOMentor(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);

        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.executeUpdate(any())).thenReturn(1);
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getString(anyInt())).thenReturn("test");
        when(mockResultSet.getInt(anyInt())).thenReturn(1);
    }

}