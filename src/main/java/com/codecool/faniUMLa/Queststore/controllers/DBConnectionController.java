package com.codecool.faniUMLa.Queststore.controllers;

import java.sql.*;

class DBConnectionController {

    public Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/codecool_quest",
                            "mikolaj", "test");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return c;
    }
    ResultSet executeQuery(String query, Connection c) {
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

    void updateQuery(String query, Connection c) {
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

