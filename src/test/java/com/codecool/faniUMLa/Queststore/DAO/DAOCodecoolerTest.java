package com.codecool.faniUMLa.Queststore.DAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import javax.sql.DataSource;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class DAOCodecoolerTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private DAOCodecoolerInterface daoCodecooler;

    private static final int userId = 1;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        daoCodecooler = new DAOCodecooler(connection);
    }

    @Test
    void testGetCoolcoins() throws SQLException {

        when(resultSet.getInt("coolcoins")).thenReturn(50);


        int expectedCoolcoins = 50;

        int actualCoolcoins = daoCodecooler.getCoolcoins(userId);

        assertEquals(expectedCoolcoins, actualCoolcoins);
    }


    @Test
    void testGetLevel() throws SQLException {

        when(resultSet.getString("id_level")).thenReturn("advenced");

        String expectedLevel = "advenced";

        String actualLevel = daoCodecooler.getLevel(userId);

        assertEquals(expectedLevel, actualLevel);
    }

    @Test
    void testBuyArtifactIfEnoughtMoney() throws SQLException {
        int idArtifact = 1;
        int artifactPrice = 30;
        int userCoolcoins = 40;

        generateMocksOfBuyArtifact(artifactPrice, userCoolcoins);

        boolean isBoughtArtifact = daoCodecooler.buyArtifact(userId, idArtifact);
        assertTrue(isBoughtArtifact);
    }

    @Test
    void testBuyArtifactIfNotEnoughtMoney() throws SQLException {
        int idArtifact = 1;
        int artifactPrice = 20;
        int userCoolcoins = 10;

        generateMocksOfBuyArtifact(artifactPrice, userCoolcoins);

        boolean isBoughtArtifact = daoCodecooler.buyArtifact(userId, idArtifact);
        assertFalse(isBoughtArtifact);
    }

    private void generateMocksOfBuyArtifact(int artifactPrice, int userCoolcoins) throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getInt(any(Integer.class))).thenReturn(artifactPrice);
        when(resultSet.getInt("coolcoins")).thenReturn(userCoolcoins);
    }

}