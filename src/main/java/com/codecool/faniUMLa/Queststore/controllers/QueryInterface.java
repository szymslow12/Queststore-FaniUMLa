package com.codecool.faniUMLa.Queststore.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public interface QueryInterface {

    default ResultSet executeQuery(String query, Connection c) {
        Statement stmt;
        ResultSet rs = null;

        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }
    default void updateQuery(String query, Connection c) {
        Statement stmt;
        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

