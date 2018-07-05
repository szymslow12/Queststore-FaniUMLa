package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.Admin;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;
import com.codecool.faniUMLa.Queststore.model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOLogin {
    Connection connection;

    public DAOLogin(Connection connection) {
        this.connection = connection;
    }

    public User logIn(String login,  String password) {
        User user = null;
        ResultSet rs;
        PreparedStatement query;
        String USER = "SELECT * FROM users WHERE user_login = ? AND user_password = ?";
        try {
            query = connection.prepareStatement(USER);
            query.setString(1, login);
            query.setString(2, password);
            rs = query.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id_user");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String user_access = rs.getString("user_access");
                if(user_access.equals("ADMIN")) {
                    return new Admin(id, first_name, last_name, email, phone_number);
                } else if (user_access.equals("MENTOR")) {
                    return new Mentor(id, first_name, last_name, email, phone_number);
                } else if (user_access.equals("CODECOOLER")) {
                    String CODECOOLER = "SELECT * FROM codecoolers WHERE id = id_user";
                    try {
                        query = connection.prepareStatement(CODECOOLER);
                        rs = query.executeQuery();
                        while (rs.next()) {
                            Integer classID = rs.getInt("id_class");
                            Integer coolcoins = rs.getInt("coolcoins");
                            Integer id_level = rs.getInt("id_level");
                            Integer id_codecooler = rs.getInt("id_codecooler");
                            return new Codecooler(id, first_name, last_name, email, phone_number, classID,
                                    id_level, coolcoins, id_codecooler);
                        }
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
