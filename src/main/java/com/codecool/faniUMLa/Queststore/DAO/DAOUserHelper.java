package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUserHelper {
    private Connection connection;
    private final String GET_USER = "SELECT * FROM users " +
            "INNER JOIN mentors " +
            "INNER JOIN admins " +
            "INNER JOIN codcoolers " +
            "ON (users.id_user = mentors.id_mentor OR " +
            "users.id_user = admins.id_admin OR" +
            "users.id_user = codecoolers.id_codecooler)" +
            "WHERE login= ? AND user_password= ?";


    public DAOUserHelper(Connection connection) {
        this.connection = connection;
    }

    public User getIfExist(String login, String password) {
        ResultSet rs = null;
        try {
            rs = prepareQuery(login, password).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareQuery(String login, String password) {
        PreparedStatement querry = null;
        try {
            querry = connection.prepareStatement(GET_USER);
            querry.setString( 1, login);
            querry.setString( 2, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return querry;

    }
}
