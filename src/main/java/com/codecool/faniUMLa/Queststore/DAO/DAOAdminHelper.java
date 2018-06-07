package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DAOAdminHelper {
    private Connection connection;

    private static final String ADD_CLASS = "INSERT INTO classes (class_name)\n" +
                                             "VALUES(?) ";

    public DAOAdminHelper(Connection connection) {
        this.connection = connection;
    }

    public void addClass(String className) {
        ResultSet rs = null;
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement(ADD_CLASS);
            query.setString(1, className);
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("There's such class already");
        }
    }
}
