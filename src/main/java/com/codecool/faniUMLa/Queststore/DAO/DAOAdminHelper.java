package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DAOAdminHelper {
    private Connection connection;

    private static final String ADD_CLASS = "INSERT INTO classes (class_name)\n" +
                                             "VALUES(?) ";
    private static final String ADD_MENTOR = "INSERT INTO users (first_name, last_name, email, phone_number, user_login, user_password, user_access) " +
                                                "VALUES(?,?,?,?,?,?,?)";

    private static final String FIND_ID = "SELECT id_user FROM users WHERE first_name = ? AND " +
            "last_name = ? AND email = ? AND phone_number = ? AND user_login = ? AND user_password = ?";

    private static final String AD_USERID_TO_MENTORS = "INSERT INTO mentors (id_user) VALUES (?)";


    public DAOAdminHelper(Connection connection) {
        this.connection = connection;
    }

    public void addClass(String className) {
        PreparedStatement query;
        try {
            query = connection.prepareStatement(ADD_CLASS);
            query.setString(1, className);
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("There's such class already");
        }
    }

    public void addUserToDataBase(ArrayList<String> userData) {
        PreparedStatement query = prepareUser(userData);
        try {
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    PreparedStatement prepareUser(ArrayList<String> userData) {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement(ADD_MENTOR);
            for(int i = 0; i< userData.size(); i++) {
                query.setString(i+1, userData.get(i));
            }//MENTOR!!!!!
            query.setString(7, "MENTOR");
        } catch (SQLException e) {
            System.out.println("Couldn't add mentor");
        }
        return query;
    }


    public void updateMentors(ArrayList<String> userData) {
        Integer user_id = getUserId(userData);
        try {
            PreparedStatement query = connection.prepareStatement(AD_USERID_TO_MENTORS);
            query.setInt(1,user_id);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private Integer getUserId(ArrayList<String> userData) {
        ResultSet rs;
        PreparedStatement query = prepareQuerryForGetUserID(userData);
        Integer user_id = null;
        try {
            rs = query.executeQuery();
            while(rs.next()) {
                user_id = rs.getInt("id_user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user_id;

    }
    private PreparedStatement prepareQuerryForGetUserID(ArrayList<String>userData) {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement(FIND_ID);
            for(int i = 0; i< userData.size(); i++) {
                query.setString(i+1, userData.get(i));
            }
        } catch (SQLException e) {
            System.out.println("Couldn't add mentor");
        }
        return query;
    }
}
