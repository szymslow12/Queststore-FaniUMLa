package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DAOMentorTest {
    private DAOMentor daoMentor;
    private Connection mockConnection;
    private DataSource mockDataSource;
    private PreparedStatement mockPreparedStmnt;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockDataSource = mock(DataSource.class);
        mockPreparedStmnt = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    void testGetAllStudents() throws SQLException {
        daoMentor = new DAOMentor(mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.executeUpdate(any())).thenReturn(1);

        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);

        List<Codecooler> studentsActual = daoMentor.getAllStudents();

        assertEquals(1, studentsActual.size());
        assertEquals(0, studentsActual.get(0).getCoolcoins());
    }

}