package com.codecool.faniUMLa.Queststore.DAO;

import com.codecool.faniUMLa.Queststore.model.store.Level;
import com.codecool.faniUMLa.Queststore.model.users.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DAOAdminHelper {
    private Connection connection;

    private final String ADD_CLASS = "INSERT INTO classes (class_name)\n" +
                                                "VALUES(?) ";
    private final String ADD_MENTOR = "INSERT INTO users (first_name, last_name, email, phone_number, user_login, user_password, user_access) " +
                                                "VALUES(?,?,?,?,?,?,?)";

    private final String FIND_ID = "SELECT id_user FROM users WHERE first_name = ? AND " +
                                    "last_name = ? AND email = ? AND phone_number = ? AND user_login = ? " +
                                    "AND user_password = ?";

    private final String AD_USERID_TO_MENTORS = "INSERT INTO mentors (id_user) VALUES (?)";

    private final String ADD_LEVEL = "INSERT INTO levels (level_name, threshold_level) VALUES(?,?) ";

    private final String GET_ALL_CLASSES = "SELECT * FROM classes";

    private final String GET_ALL_LEVELS = "SELECT * FROM levels";

    private final String DELETE_MENTOR = "DELETE FROM users WHERE id_user = ?";
    private final String DELETE_CLASS = "DELETE FROM classes WHERE id_class = ?";
    private final String DELETE_LEVEL = "DELETE FROM levels WHERE id_level = ?";

    private final String UPDATE_CLASS = "UPDATE classes SET class_name= ? WHERE id_class = ?";
    private final String UPDATE_LEVEL = "UPDATE levels SET level_name  = ?, threshold_level= ? WHERE id_level = ?";

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

    public Map<Integer, String> getAllClasses() {
        Map<Integer, String> classList = new HashMap<>();
        ResultSet rs;
        PreparedStatement query;
        try {
            query = connection.prepareStatement(GET_ALL_CLASSES);
            rs = query.executeQuery();
            while (rs.next()) {
                String class_name = rs.getString("class_name");
                Integer class_id = rs.getInt("id_class");
                classList.put(class_id, class_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classList;
    }

    public ArrayList<Level> getAllLevels() {
        ArrayList<Level> levelList = new ArrayList<>();
        ResultSet rs;
        PreparedStatement query;
        try {
            query = connection.prepareStatement(GET_ALL_LEVELS);
            rs = query.executeQuery();
            while (rs.next()) {
                Integer id_level = rs.getInt("id_level");
                String level_name = rs.getString("level_name");
                Integer threshold_level = rs.getInt("threshold_level");

                levelList.add(new Level(id_level, level_name, threshold_level));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return levelList;
    }

    public void addUserToDataBase(ArrayList<String> userData) {
        PreparedStatement query = prepareUser(userData);
        try {
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareUser(ArrayList<String> userData) {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement(ADD_MENTOR);
            for(int i = 0; i< userData.size(); i++) {
                query.setString(i+1, userData.get(i));
            }
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

    public PreparedStatement prepareQueryForUpdatedMentor(String column_name, String changedWord, Integer idUser) {
        PreparedStatement query = null;
        String UPDATE_COLUMN = "UPDATE users SET "+ column_name + " = " + "?" +  " WHERE id_user=" + idUser +";";
        try {
            query = connection.prepareStatement(UPDATE_COLUMN);
            query.setString(1, changedWord);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public void editClass(int index, String name) {
        PreparedStatement query;
        try {
            query = connection.prepareStatement(UPDATE_CLASS);
            query.setString(1, name);
            query.setInt(2, index);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editLevel(int index, String name, int exp) {
        PreparedStatement query;

        try {
            query = connection.prepareStatement(UPDATE_LEVEL);
            query.setString(1, name);
            query.setInt(2, exp);
            query.setInt(3, index);
            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLevel(String level_name, Integer threshold_level) {
        PreparedStatement query;
        try {
            query = connection.prepareStatement(ADD_LEVEL);
            query.setString(1, level_name);
            query.setInt(2, threshold_level);
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("There's such level already");
        }
    }

    public void deleteMentor(int index) {

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_MENTOR);
            statement.setInt(1, index);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClass(int index) {

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CLASS);
            statement.setInt(1, index);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLevel(int index) {

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_LEVEL);
            statement.setInt(1, index);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

