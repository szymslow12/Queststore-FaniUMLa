package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;

public class DAOMentor implements DAOMentorInterface {
    private DAOMentorHelper helper;
    Connection connection;

    public DAOMentor(Connection connection) {
        this.connection = connection;
        helper = new DAOMentorHelper(connection);
    }

    public void createClass() {

    }

}
