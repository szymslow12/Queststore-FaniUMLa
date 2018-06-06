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

public class DAOUserHelper {
    private Connection connection;
    private final String GET_USER_ACCESS_ID = "SELECT user_acces, id_user FROM users "+
                                    "WHERE login= ? AND user_password= ?";
    private final String GET_MENTOR = "SELECT * FROM mentors "+
            "WHERE id_user =  ?";
    private final String GET_ADMIN = "SELECT * FROM admins "+
            "WHERE id_user =  ?";
    private final String GET_CODECOOLER = "SELECT * FROM codecoolers "+
            "WHERE id_user =  ?";


    public DAOUserHelper(Connection connection) {
        this.connection = connection;
    }

    public User getIfExist(String login, String password) {
        User user = null;
        ResultSet rs = null;
        PreparedStatement query = null;
        ArrayList accessID = getUserAccessID(login, password);
        int accessIndex = 0;
        int idUser = 1;

        String access = (String) accessID.get(accessIndex);
        String id = (String) accessID.get(idUser);
        try {
            switch (access) {
                case "MENTOR":
                    query = connection.prepareStatement(GET_MENTOR);
                    query.setString(1, id);
                    rs = query.executeQuery();
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
                    query = connection.prepareStatement(GET_ADMIN);
                    query.setString(1, id);
                    rs = query.executeQuery();
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
                    query.setString(1, id);
                    rs = query.executeQuery();
                    while (rs.next()) {
                        Integer id_User = rs.getInt("id_user");
                        String first_name = rs.getString("first_name");
                        String last_name = rs.getString("last_name");
                        String email = rs.getString("email");
                        String phone_number = rs.getString("phone_number");
                        int class_id = rs.getInt("class_id");
                        String experience = rs.getString("experience");
                        int coolcoins = rs.getInt("coolcoins");
                        user = new Codecooler(id_User, first_name, last_name, email, phone_number, class_id, experience, coolcoins);
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    private PreparedStatement prepareAccessQuery(String login, String password) throws SQLException {
        PreparedStatement query = null;

        query = connection.prepareStatement(GET_USER_ACCESS_ID);
        query.setString( 1, login);
        query.setString( 2, password);

        return query;
    }

    private ArrayList<String> getUserAccessID(String login, String password) {
        ArrayList<String> userAccessID = new ArrayList<>();
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
