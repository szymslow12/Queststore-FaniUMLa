package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.users.Admin;
import com.codecool.faniUMLa.Queststore.model.users.Codecooler;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;
import com.codecool.faniUMLa.Queststore.model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOUserHelper {
    private Connection connection;
    private final String GET_USER_ACCESS_ID = "SELECT user_access, id_user FROM users "+
                                    "WHERE user_login= ? AND user_password= ?";
    private final String GET_USER = "SELECT * FROM users "+
            "WHERE id_user =  ?";
    private final String GET_CODECOOLER = "SELECT * FROM users INNER JOIN codecoolers ON (users.id_user = codecoolers.id_user) WHERE users.id_user =  ?";

    DAOUserHelper(Connection connection) {
        this.connection = connection;
    }

    User getIfExist(String login, String password) {
        User user = null;
        ResultSet rs;
        PreparedStatement query;
        List<String> accessID = getUserAccessID(login, password);
        int accessIndex = 0;
        int idUser = 1;

        if(accessID.size() < 1) {
            return null;
        }

        String access = (String) accessID.get(accessIndex);
        Integer id =  Integer.valueOf((String)accessID.get(idUser));
        try {
            query = connection.prepareStatement(GET_USER);
            query.setInt(1, id);
            rs = query.executeQuery();
            switch (access) {
                case "MENTOR":

                    while (rs.next()) {
                        Integer id_User = rs.getInt("id_user");
                        String first_name = rs.getString("first_name");
                        String last_name = rs.getString("last_name");
                        String email = rs.getString("email");
                        String phone_number = rs.getString("phone_number");
                        user = new Mentor(id_User, first_name, last_name, email, phone_number);
                    }
                    break;
                case "ADMIN":

                    while (rs.next()) {
                        Integer id_User = rs.getInt("id_user");
                        String first_name = rs.getString("first_name");
                        String last_name = rs.getString("last_name");
                        String email = rs.getString("email");
                        String phone_number = rs.getString("phone_number");
                        user = new Admin(id_User, first_name, last_name, email, phone_number);
                    }
                    break;
                case "CODECOOLER":
                    query = connection.prepareStatement(GET_CODECOOLER);
                    query.setInt(1, id);
                    rs = query.executeQuery();
                    while (rs.next()) {
                        Integer id_User = rs.getInt("id_user");
                        String first_name = rs.getString("first_name");
                        String last_name = rs.getString("last_name");
                        String email = rs.getString("email");
                        String phone_number = rs.getString("phone_number");
                        int id_codecooler = rs.getInt("id_codecooler");
                        int class_id = rs.getInt("id_class");
                        int level_id = rs.getInt("id_level");
                        int coolcoins = rs.getInt("coolcoins");
                        user = new Codecooler(idUser, first_name, last_name, email, phone_number, class_id, level_id, coolcoins, id_codecooler);
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private PreparedStatement prepareAccessQuery(String login, String password) throws SQLException {
        PreparedStatement query;
        query = connection.prepareStatement(GET_USER_ACCESS_ID);
        query.setString( 1, login);
        query.setString( 2, password);
        return query;
    }

    private List<String> getUserAccessID(String login, String password) {
        List<String> userAccessID = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = prepareAccessQuery(login, password).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                userAccessID.add(rs.getString("user_access"));
                userAccessID.add(rs.getString("id_user"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAccessID;
    }
}
