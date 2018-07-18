package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.store.Artifact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testShowArtifacts() throws SQLException {
        int categoryId = 1;
        List<Artifact> artifactList = new ArrayList<>();
        artifactList.add(new Artifact(1, "Artifact 1", 1, 20, "Description"));
        artifactList.add(new Artifact(2, "Artifact 2", 1, 30, "Description"));

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when((preparedStatement.executeQuery())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getInt("id_artifact")).thenReturn(1).thenReturn(2);
        when(resultSet.getString("artifact_name")).thenReturn("Artifact 1").thenReturn("Artifact 2");
        when(resultSet.getInt("category_id")).thenReturn(1);
        when(resultSet.getInt("price")).thenReturn(20).thenReturn(30);
        when(resultSet.getString("description")).thenReturn("Description");

        List<Artifact> expectedArtifactList = daoCodecooler.showArtifacts(categoryId);
        for (int i = 0; i < artifactList.size(); i++) {
            assertIfArtifactTheSame(artifactList.get(i), expectedArtifactList.get(i));
        }
    }

    private void assertIfArtifactTheSame(Artifact artifact1, Artifact artifact2 ) {
        assertEquals(artifact1.getArtifactID(), artifact2.getArtifactID());
        assertEquals(artifact1.getName(), artifact2.getName());
        assertEquals(artifact1.getCategory(), artifact2.getCategory());
        assertEquals(artifact1.getPrice(), artifact2.getPrice());
        assertEquals(artifact1.getDescription(), artifact2.getDescription());
    }


    private void generateMocksOfBuyArtifact(int artifactPrice, int userCoolcoins) throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getInt(any(Integer.class))).thenReturn(artifactPrice);
        when(resultSet.getInt("coolcoins")).thenReturn(userCoolcoins);
    }

}