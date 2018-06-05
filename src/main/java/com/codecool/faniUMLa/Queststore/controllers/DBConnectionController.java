package com.codecool.faniUMLa.Queststore.controllers;

import java.sql.Connection;
import java.sql.DriverManager;

class DBConnectionController {

    public Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/codecool_quest",
                            "elzbietakrzych", "");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return c;
    }
}

