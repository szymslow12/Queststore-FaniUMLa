package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.User;

import java.sql.Connection;

public class DAOUser implements DAOUserInterface {
    private DAOUserHelper helper;
    Connection connection;

    public DAOUser(Connection connection) {
        this.connection = connection;
        helper = new DAOUserHelper(connection);
    }

    public User getUser(String login, String password) {
        User user = null;
        user = helper.getIfExist(login,password);
        return user;
    }
}
