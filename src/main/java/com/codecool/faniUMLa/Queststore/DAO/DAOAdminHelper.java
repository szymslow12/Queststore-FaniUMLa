package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;

public class DAOMentorHelper {
    private Connection connection;

    public DAOMentorHelper(Connection connection) {
        this.connection = connection;
    }
}
