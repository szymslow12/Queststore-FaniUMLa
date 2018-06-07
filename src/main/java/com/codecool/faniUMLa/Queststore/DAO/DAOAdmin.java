package com.codecool.faniUMLa.Queststore.DAO;

import java.sql.Connection;

public class DAOAdmin implements DAOAdminInterface {
    private DAOAdminHelper helper;
    Connection connection;

    public DAOAdmin(Connection connection) {
        this.connection = connection;
        helper = new DAOAdminHelper(connection);
    }

    public void createClass(String className) {
        helper.addClass(className);
    }

    public void editMentor(String choosenMentor) {
        
    }

}
