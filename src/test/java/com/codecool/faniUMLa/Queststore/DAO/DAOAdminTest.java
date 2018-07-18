package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.Classroom;
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

class DAOAdminTest {
    private DAOAdmin daoAdmin;
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
    void testAddClass() throws SQLException {
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


}